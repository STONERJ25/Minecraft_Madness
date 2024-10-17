CREATE DATABASE IF NOT EXISTS Minecraft_Madness;

USE Minecraft_Madness;

CREATE TABLE Users (
    UserID INT PRIMARY KEY,
    Username VARCHAR(45) NOT NULL,
    Email VARCHAR(45) NOT NULL,
    Password VARCHAR(45) NOT NULL,
    Admin_id VARCHAR(45)
);

CREATE TABLE Minecraft_Server (
    server_id INT PRIMARY KEY,
    ip_address INT NOT NULL,
    port INT NOT NULL,
    UserID INT,  
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE Picture (
    PictureID INT PRIMARY KEY,
    FilePath VARCHAR(45) NOT NULL,
    UploadDate VARCHAR(45),
    UserID INT, 
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);