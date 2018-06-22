import static spark.Spark.*

Initializer.loadNatives()
get '/hello', { req, res -> 'Hello from your groovy Sparkberry Pi!' }
Processor.openCamera()


