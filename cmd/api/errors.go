package main

import "net/http"

func (app *Application) writeBadRequestResponse(w http.ResponseWriter, r *http.Request) {
	message := "Malformed request. Please try again"
	app.writeError(w, r, http.StatusBadRequest, http.StatusBadRequest, message)
}

func (app *Application) writeInternalServerErrorResponse(w http.ResponseWriter, r *http.Request) {
	message := "Server encounters uan unknown error. Please try again later"
	app.writeError(w, r, http.StatusInternalServerError, http.StatusInternalServerError, message)
}

func (app *Application) writeInvalidAuthenticationErrorResponse(w http.ResponseWriter, r *http.Request) {
	message := "Missing token or invalid token"
	app.writeError(w, r, http.StatusUnauthorized, http.StatusUnauthorized, message)
}

func (app *Application) writeUserActivationRequiredResponse(w http.ResponseWriter, r *http.Request) error {
	return app.writeResponse(w, nil, http.StatusUnauthorized, responseHeader{"x-sg-auth-req": "ACTIVATION"})
}

func (app *Application) writeForceUpdateResponse(w http.ResponseWriter, r *http.Request) error {
	return app.writeResponse(w, nil, http.StatusBadRequest, responseHeader{"x-sg-auth-forbidden": "APP_NOT_SUPPORTED"})
}
