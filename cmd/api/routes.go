package main

import (
	"fmt"
	"github.com/julienschmidt/httprouter"
	"net/http"
)

func (app *Application) routes() http.Handler {
	httpRouter := httprouter.New()

	httpRouter.NotFound = http.HandlerFunc(app.notFound)
	httpRouter.MethodNotAllowed = http.HandlerFunc(app.methodNotAllowed)

	return app.recoverPanic(httpRouter)
}

// Custom method not allow handler
func (app *Application) methodNotAllowed(w http.ResponseWriter, r *http.Request) {
	message := fmt.Sprintf("The method '%s' is not supported!", r.Method)
	app.writeError(w, r, http.StatusMethodNotAllowed, http.StatusMethodNotAllowed, message)
}

// Custom not found handler
func (app *Application) notFound(w http.ResponseWriter, r *http.Request) {
	message := fmt.Sprintf("The URL '%s' is not found", r.URL.Path)
	app.writeError(w, r, http.StatusNotFound, http.StatusNotFound, message)
}
