package models

import "gorm.io/gorm"

type Product struct {
	gorm.Model
	Name       string
	Price      float64
	CategoryID uint
	Category   Category
}

type Category struct {
	gorm.Model
	Name     string
	Products []Product
}

type Cart struct {
	gorm.Model
	UserID    uint
	ProductID uint
	Quantity  int
}

type User struct {
	gorm.Model
	Name string
}

type Order struct {
	gorm.Model
	UserID uint
	Total  float64
}

func ProductByCategory(categoryID uint) func(db *gorm.DB) *gorm.DB {
	return func(db *gorm.DB) *gorm.DB {
		return db.Where("category_id = ?", categoryID)
	}
}
