ARG GOLANG_IMAGE="golang:1.21.3"
ARG BASE_IMAGE="gcr.io/distroless/base-debian12:nonroot-amd64"

FROM --platform=linux/amd64 $GOLANG_IMAGE as builder
WORKDIR /FEM
COPY ../../../go.mod ./
RUN go mod download

COPY . ./
WORKDIR ./cmd/api
RUN env GOOS=linux GOARCH=amd64 go build -o ./fem_binary

FROM $BASE_IMAGE
COPY --from=builder /FEM/cmd/api/fem_binary ./

CMD ["./fem_binary"]