package controllers

import (
	"Zad4_Go/database"
	"Zad4_Go/models"
	"net/http"
	"strconv"

	"github.com/labstack/echo/v4"
)

func CreateProduct(c echo.Context) error {
	var product models.Product
	if err := c.Bind(&product); err != nil {
		return c.JSON(http.StatusBadRequest, err)
	}
	database.DB.Create(&product)
	return c.JSON(http.StatusCreated, product)
}

func GetProducts(c echo.Context) error {
	var products []models.Product
	database.DB.Preload("Category").Find(&products)
	return c.JSON(http.StatusOK, products)
}

func GetProduct(c echo.Context) error {
	id, err := strconv.Atoi(c.Param("id"))
	if err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "invalid product id"})
	}

	var product models.Product
	result := database.DB.Preload("Category").First(&product, id)
	if result.Error != nil {
		return c.JSON(http.StatusNotFound, map[string]string{"error": "product not found"})
	}
	return c.JSON(http.StatusOK, product)
}

func UpdateProduct(c echo.Context) error {
	id, err := strconv.Atoi(c.Param("id"))
	if err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "invalid product id"})
	}

	var product models.Product
	if err := database.DB.First(&product, id).Error; err != nil {
		return c.JSON(http.StatusNotFound, map[string]string{"error": "product not found"})
	}

	if err := c.Bind(&product); err != nil {
		return c.JSON(http.StatusBadRequest, err)
	}

	database.DB.Save(&product)
	return c.JSON(http.StatusOK, product)
}

func DeleteProduct(c echo.Context) error {
	id, err := strconv.Atoi(c.Param("id"))
	if err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "invalid product id"})
	}

	if res := database.DB.Delete(&models.Product{}, id); res.RowsAffected == 0 {
		return c.JSON(http.StatusNotFound, map[string]string{"error": "product not found"})
	}

	return c.NoContent(http.StatusNoContent)
}

func GetProductsByCategory(c echo.Context) error {
	categoryIDParam := c.Param("categoryId")
	var products []models.Product

	categoryID, err := strconv.Atoi(categoryIDParam)
	if err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "Invalid category ID"})
	}

	database.DB.Scopes(models.ProductByCategory(uint(categoryID))).
		Preload("Category").
		Find(&products)

	return c.JSON(http.StatusOK, products)
}
