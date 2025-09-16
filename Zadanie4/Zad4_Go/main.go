package main

import (
	"Zad4_Go/controllers"
	"Zad4_Go/database"

	"github.com/labstack/echo/v4"
)

func main() {
	e := echo.New()

	database.Connect()

	//Product endpoints
	e.POST("/products", controllers.CreateProduct)
	e.GET("/products", controllers.GetProducts)
	e.GET("/products/category/:categoryId", controllers.GetProductsByCategory)
	e.GET("/products/:id", controllers.GetProduct)
	e.PUT("/products/:id", controllers.UpdateProduct)
	e.PATCH("/products/:id", controllers.UpdateProduct)
	e.DELETE("/products/:id", controllers.DeleteProduct)

	//Cart endpoints
	e.POST("/cart", controllers.AddToCart)

	e.Logger.Fatal(e.Start(":8080"))
}
