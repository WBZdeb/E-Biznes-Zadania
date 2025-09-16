package controllers

import (
	"Zad4_Go/database"
	"Zad4_Go/models"
	"github.com/labstack/echo/v4"
	"net/http"
)

func AddToCart(c echo.Context) error {
	var cart models.Cart
	if err := c.Bind(&cart); err != nil {
		return c.JSON(http.StatusBadRequest, err)
	}
	database.DB.Create(&cart)
	return c.JSON(http.StatusCreated, cart)
}
