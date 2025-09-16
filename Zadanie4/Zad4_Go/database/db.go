package database

import (
	"Zad4_Go/models"
	"fmt"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"log"
	_ "modernc.org/sqlite"
	"os"
)

var DB *gorm.DB

func Connect() {
	// Sprawdzenie ścieżki roboczej
	wd, _ := os.Getwd()
	fmt.Println("📁 Working directory:", wd)

	// Próbuj otworzyć bazę
	db, err := gorm.Open(sqlite.Open("C:/Users/wojci/Desktop/store.db"), &gorm.Config{})
	if err != nil {
		log.Fatalf("❌ gorm.Open error: %v", err) // <-- to wypisze dokładny problem
	}

	// Automigracja
	if err := db.AutoMigrate(
		&models.Category{},
		&models.Product{},
		&models.Cart{},
		&models.User{},
		&models.Order{},
	); err != nil {
		log.Fatalf("❌ AutoMigrate error: %v", err)
	}

	DB = db
	fmt.Println("✅ Database connected")
}
