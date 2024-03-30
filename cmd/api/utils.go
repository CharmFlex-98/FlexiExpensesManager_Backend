package main

import (
	"encoding/json"
	"errors"
	"fmt"
	"github.com/julienschmidt/httprouter"
	"io"
	"net/http"
	"net/url"
	"os"
	"strconv"
)

// Logging
func (app *Application) logInfo(message string, args ...any) {
	app.logger.Info(message, args)
}

func (app *Application) logError(error error, r *http.Request) {
	app.logger.Error(error.Error(), "METHOD", r.Method)
}

func (app *Application) logWarning(message string, args ...any) {
	app.logger.Error(message, args)
}

type responseData map[string]any
type responseHeader map[string]string

// Json response
func (app *Application) writeResponse(w http.ResponseWriter, content any, code int, headers responseHeader) error {
	res, err := json.MarshalIndent(content, "", "\t")
	if err != nil {
		return err
	}

	for key, value := range headers {
		w.Header().Set(key, value)
	}
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(code)
	w.Write(res)

	return nil
}

func (app *Application) writeError(w http.ResponseWriter, r *http.Request, code int, errorCode int, message any) {
	errContent := map[string]any{
		"errorCode": errorCode,
		"message":   message,
	}
	data := responseData{
		"error": errContent,
	}

	err := app.writeResponse(w, data, code, nil)
	if err != nil {
		app.logError(err, r)
		w.WriteHeader(500)
	}
}

func (app *Application) readRequest(r *http.Request, input any) error {
	reqBody := r.Body

	err := json.NewDecoder(reqBody).Decode(&input)
	if err != nil {
		return err
	}

	return nil
}

func (app *Application) readParam(paramName string, r *http.Request) (*int64, error) {
	params := httprouter.ParamsFromContext(r.Context())
	value, err := strconv.ParseInt(params.ByName(paramName), 10, 64)
	if err != nil {
		return nil, err
	}

	return &value, nil
}

func (app *Application) readString(args url.Values, key string, defaultValue string) (string, error) {
	val := args.Get(key)

	if val == "" {
		return defaultValue, nil
	}

	return val, nil
}

func (app *Application) readInt(args url.Values, key string, defaultValue int64) (int64, error) {
	val := args.Get(key)

	if val == "" {
		return defaultValue, nil
	}

	valInInt, err := strconv.ParseInt(val, 10, 64)
	if err != nil {
		return defaultValue, err
	}

	return valInInt, nil
}

func readJsonFromFile(filePath string, item interface{}) error {
	file, err := os.Open(filePath)
	if err != nil {
		return err
	}
	defer file.Close()

	// Read the file content
	content, err := io.ReadAll(file)
	if err != nil {
		return err
	}

	err = json.Unmarshal(content, &item)
	if err != nil {
		return err
	}

	return nil
}

func (app *Application) background(fn func(), r *http.Request) {
	// increment before start
	app.wg.Add(1)
	go func() {
		// Decrement before end
		defer app.wg.Done()

		// Task run in goroutine need another recover handler, it won't propagate up to our upstream recover() checker.
		defer func() {
			if err := recover(); err != nil {
				app.logError(errors.New(fmt.Sprintf("%s", err)), r)
			}
		}()

		// Execute background goroutine
		fn()
	}()
}
