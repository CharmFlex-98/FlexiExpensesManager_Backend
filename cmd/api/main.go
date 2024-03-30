package main

import (
	"context"
	"database/sql"
	"flag"
	_ "github.com/jackc/pgx/v5/stdlib"
	"log/slog"
	"os"
	"sync"
	"time"
)

type config struct {
	port     int
	env      string
	dbConfig struct {
		dsn               string
		maxOpenConnection int
		maxIdleConnection int
		maxIdleTime       time.Duration
	}
}

type Application struct {
	config config
	logger *slog.Logger
	wg     sync.WaitGroup
}

func main() {
	config := config{}
	logger := slog.New(slog.NewTextHandler(os.Stdout, nil))

	flag.IntVar(&config.port, "port", 3000, "Port for listening")
	config.env = os.Getenv("ENV")
	if config.env == "" {
		config.env = "DEV"
	}

	flag.Parse()

	_, err := config.openDatabase()
	if err != nil {
		logger.Error(err.Error())
		os.Exit(1)
	}

	app := Application{
		config: config,
		logger: logger,
	}

	err = app.serve()
	if err != nil {
		app.logInfo("error: %s, stopping server...", err)
		os.Exit(1)
	}
}

func (cfg config) openDatabase() (*sql.DB, error) {
	db, err := sql.Open("pgx", os.Getenv("FEM_DATABASE_URL"))
	if err != nil {
		return nil, err
	}

	db.SetMaxOpenConns(cfg.dbConfig.maxOpenConnection)
	db.SetMaxIdleConns(cfg.dbConfig.maxIdleConnection)
	db.SetConnMaxIdleTime(cfg.dbConfig.maxIdleTime)

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	err = db.PingContext(ctx)
	if err != nil {
		return nil, err
	}

	return db, nil
}

func (c config) isProd() bool {
	return c.env == "PRD"
}
