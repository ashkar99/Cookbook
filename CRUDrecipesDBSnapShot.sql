-- MySQL dump 10.13  Distrib 8.0.36, for macos14 (arm64)
--
-- Host: 139.162.164.160    Database: cookbook
-- ------------------------------------------------------
-- Server version	8.0.36-0ubuntu0.23.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `owner` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `owner_ID_idx` (`owner`),
  CONSTRAINT `owner_ID` FOREIGN KEY (`owner`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;
/*!40000 ALTER TABLE `ingredient` DISABLE KEYS */;
INSERT INTO `ingredient` VALUES (1,'Sugar',NULL),(2,'Wheat Flour',NULL),(3,'Egg',NULL),(4,'Milk',NULL),(5,'Ground Meat',NULL),(6,'Yellow Onion',NULL),(7,'Mustard',NULL),(8,'Syrup',NULL),(9,'Salt',NULL),(10,'Black Pepper',NULL),(11,'Cream',NULL),(12,'Breadcrumbs',NULL),(13,'Butter',NULL),(14,'Potato',NULL),(15,'Lingon Berries',NULL),(16,'Honey',NULL),(17,'Celery Stalk',NULL),(18,'Garlic',NULL),(19,'Olive Oil',NULL),(20,'Tomato Puré',NULL),(21,'Red Wine',NULL),(22,'Crushed Tomatoes',NULL),(23,'Thyme',NULL),(24,'Rosemary',NULL),(25,'Bay Leaf',NULL),(26,'Lasagne Sheets',NULL),(27,'Aged Cheese',NULL),(28,'Parmesan',NULL),(29,'Muscat',NULL),(30,'Arugula',NULL),(31,'Cherry Tomatoes',NULL),(32,'Carrot',NULL),(33,'Balsamico',NULL),(34,'Dried Chickpeas',NULL),(35,'Parsley',NULL),(36,'Cilantro',NULL),(37,'Ground Cumin',NULL),(38,'Rapeseed Oil',NULL),(39,'Yoghurt',NULL),(40,'Mayonnaise',NULL),(41,'Water',NULL),(42,'Garlicpowder',NULL),(43,'Chickpea Broth',NULL),(44,'Lemon Juice',NULL),(45,'Liba Bread',NULL),(46,'Chili Sauce',NULL),(47,'Pickles',NULL),(48,'Tomato',NULL),(49,'Red Onion',NULL),(50,'Iceberg Lettuce',NULL),(51,'Fefferoni',NULL),(52,'Aubergine',NULL),(53,'Pancetta',NULL),(54,'Vodka',NULL),(55,'Canned Cherry Tomatoes',NULL),(56,'Tabasco',NULL),(57,'Pasta (Penne)',NULL),(58,'Chicken Breast',NULL),(59,'Sourdough Bread',NULL),(60,'Egg Yolk',NULL),(61,'White Wine Vinegar',NULL),(62,'Dijon Mustard',NULL),(63,'Cooking Oil',NULL),(64,'Bacon',NULL),(65,'Romaine Lettuce',NULL),(66,'Haddock Fillet',NULL),(67,'Corn Starch',NULL),(68,'Baking Powder',NULL),(69,'Light Beer',NULL),(70,'Squeezed Lemon',NULL),(71,'White Pepper',NULL),(72,'Green Peas',NULL),(73,'Vinegar',NULL),(74,'Lemon',NULL),(75,'Vanilla Sugar',NULL),(76,'Egg Whites',NULL),(77,'Honey',NULL),(78,'Mixed Berries (Fresh)',NULL),(79,'Yeast',NULL),(80,'Canned Tomatoes',NULL),(81,'Mozzarella',NULL),(82,'Prosciutto',NULL),(83,'Basil',NULL),(84,'Pasta (Spaghetti)',NULL),(85,'Pecorino',NULL),(86,'Shallot',NULL),(87,'Red Chili',NULL),(88,'Ginger',NULL),(89,'Lobster Stock',NULL),(90,'Salmon Fillet',NULL),(91,'Cod Fillet',NULL),(92,'Broccoli',NULL),(93,'Peeled Shrimp',NULL),(94,'Sugar Snap Peas',NULL),(95,'Sichuan Pepper',NULL),(96,'Cashew Nuts',NULL),(97,'Chicken Thigh',NULL),(98,'Chiliflakes',NULL),(99,'Oyster Sauce',NULL),(100,'Chinese Soy Sauce',NULL),(101,'Black Rice Vinegar',NULL),(102,'Sesame Oil',NULL),(103,'Jasmine Rice',NULL),(104,'Rolled Oats',NULL),(105,'Sour Cream',NULL),(106,'Onion Soup (powder)',NULL),(107,'Chanterelle',NULL),(108,'Carnaroli Rice',NULL),(109,'Dry White Wine',NULL),(110,'Vegetable Broth',NULL),(111,'Almond',NULL),(112,'Dill',NULL),(113,'Chia Seeds',NULL),(114,'Oat Milk',NULL),(115,'Coffe (Cold)',NULL),(116,'Cacao Nibs',NULL),(117,'Coffe (Cold Brew)',NULL),(118,'Gelling Sugar',NULL),(119,'Orange Peel',NULL),(120,'Ice',NULL),(121,'Angostura Bitters',NULL),(122,'Whiskey',NULL),(123,'Spinach (Frozen)',NULL),(124,'Blueberries (Frozen)',NULL),(125,'Peanutbutter',NULL),(126,'Vanilla Powder',NULL),(127,'Quark (Vanilla)',NULL),(128,'Vinegar Essence (12%)',NULL),(129,'Burger Bun',NULL),(130,'Smoked Ham (Sliced)',NULL),(131,'Hollandaise Sauce',NULL),(132,'Chives',NULL),(133,'Paprika Powder',NULL),(134,'Cardamom (Ground)',NULL),(135,'Mixed Berries (Frozen)',NULL),(136,'Whole Wheat Flour',NULL),(137,'Powdered Sugar',NULL),(138,'Marsala Wine',NULL),(139,'Mascarpone',NULL),(140,'Ladyfingers',NULL),(141,'Espresso',NULL),(142,'Cocoa',NULL),(143,'Cream Cheese',NULL),(144,'Dulche de Leche',NULL),(145,'Gelatin Leaf',NULL),(146,'Potato Starch',NULL),(147,'Puff Pastry',NULL),(148,'Crème Fraîche',NULL),(149,'Priest Cheese',NULL),(150,'Västerbotten Cheese',NULL),(151,'Bleak Roe',NULL),(152,'Schallot',NULL),(153,'Lobster Stock',NULL),(154,'Cayennepepper',NULL),(155,'Forest Mushroom',NULL),(156,'Bell Pepper (Red)',NULL),(157,'Cannellini Beans',NULL),(158,'Simple Syrup',NULL),(159,'Orange Liqueur',NULL),(160,'Lime Juice',NULL),(161,'Canberry Juice',NULL),(162,'Orange (Zest)',NULL),(163,'TestIngredient',37),(164,'TestIngredient2',37),(167,'Maskrosor',46),(168,'Saltvatten',46),(169,'Kalksten',46),(181,'Cucumber',38),(184,'Cucumber',36),(186,'Creme Fraiche',38);
/*!40000 ALTER TABLE `ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_recipe_ingredient`
--

DROP TABLE IF EXISTS `r_recipe_ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `r_recipe_ingredient` (
  `ingredient_id` int NOT NULL,
  `recipe_id` int NOT NULL,
  `qty` double DEFAULT NULL,
  `unit` int DEFAULT NULL,
  KEY `fk_r_recipe_ingredient_1_idx` (`recipe_id`),
  KEY `fk_r_recipe_ingredient_2_idx` (`ingredient_id`),
  KEY `unit_id_idx` (`unit`),
  CONSTRAINT `fk_r_recipe_ingredient_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_r_recipe_ingredient_2` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`id`),
  CONSTRAINT `unit_id` FOREIGN KEY (`unit`) REFERENCES `unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_recipe_ingredient`
--

LOCK TABLES `r_recipe_ingredient` WRITE;
/*!40000 ALTER TABLE `r_recipe_ingredient` DISABLE KEYS */;
INSERT INTO `r_recipe_ingredient` VALUES (5,1,500,9),(6,1,1,15),(3,1,1,15),(8,1,2,4),(9,1,1,3),(10,1,1,3),(11,1,0.75,6),(4,1,2.25,6),(12,1,0.5,6),(14,1,600,9),(13,1,200,9),(15,1,NULL,19),(9,2,NULL,19),(6,2,1,15),(32,2,1,15),(17,2,2,15),(18,2,3,15),(19,2,0.5,6),(20,2,3,5),(5,2,500,9),(21,2,1.5,6),(22,2,400,9),(23,2,1,4),(24,2,1,4),(25,2,1,19),(13,2,50,9),(2,2,5,5),(4,2,8,6),(27,2,2,6),(28,2,1,6),(29,2,NULL,19),(30,2,100,9),(31,2,250,9),(19,2,NULL,19),(33,2,NULL,19),(34,3,500,9),(6,3,1,15),(18,3,3,15),(36,3,0.5,6),(37,3,2,4),(9,3,1.5,4),(10,3,NULL,19),(39,3,2,6),(40,3,1,6),(41,3,2,5),(42,3,1,4),(10,3,NULL,19),(43,3,0.5,6),(18,3,4,15),(44,3,1,5),(45,3,4,15),(46,3,NULL,19),(47,3,2,15),(48,3,1,15),(49,3,1,15),(50,3,NULL,19),(51,3,NULL,19),(38,3,2.2,7),(35,3,1.3,6),(18,4,2,15),(53,4,160,9),(54,4,1,6),(55,4,400,9),(11,4,2,6),(56,4,1,4),(57,4,300,9),(19,4,NULL,19),(9,4,NULL,19),(10,4,NULL,19),(28,4,NULL,19),(66,6,600,9),(2,6,2,6),(9,6,NULL,19),(10,6,NULL,19),(38,6,1.3,7),(67,6,1,5),(68,6,1,4),(69,6,1,6),(70,6,1,5),(71,6,NULL,19),(14,6,800,9),(60,6,2,15),(62,6,1,5),(61,6,0.5,5),(47,6,2,6),(72,6,NULL,19),(73,6,NULL,19),(74,6,NULL,19),(58,5,600,9),(18,5,3,15),(9,5,NULL,19),(59,5,12,15),(60,5,3,15),(61,5,2,4),(62,5,2,5),(63,5,3.5,6),(49,5,1,15),(64,5,8,15),(48,5,4,15),(65,5,1,15),(50,7,2,15),(4,7,1,6),(63,7,2,5),(2,7,0.75,6),(75,7,2,4),(76,7,4,15),(1,7,4,5),(9,7,1,3),(13,7,NULL,19),(77,7,NULL,19),(78,7,NULL,19),(79,8,1,4),(41,8,5,6),(2,8,700,9),(9,8,2,4),(19,8,2,5),(80,8,1,11),(81,8,300,9),(82,8,150,9),(83,8,NULL,19),(84,9,400,9),(60,9,4,15),(53,9,200,9),(85,9,30,9),(28,9,30,9),(10,9,NULL,19),(9,9,NULL,19),(86,10,2,15),(18,10,1,15),(87,10,0.5,15),(19,10,1,5),(88,10,1,5),(41,10,1,6),(89,10,2,5),(11,10,5,6),(90,10,300,9),(91,10,300,9),(92,10,250,9),(93,10,150,9),(94,10,150,9),(9,10,2,3),(10,10,2,3),(35,10,NULL,19),(95,11,1,5),(96,11,3,6),(18,11,3,15),(88,11,1,5),(97,11,500,9),(92,11,250,9),(32,11,1,15),(6,11,1,15),(98,11,2,4),(38,11,NULL,19),(99,11,1,6),(100,11,1,5),(101,11,2,5),(1,11,1,5),(102,11,1,5),(35,11,NULL,19),(103,11,4,16),(104,12,1,6),(3,12,2,15),(105,12,3,6),(5,12,1,10),(106,12,1,14),(10,12,0.5,4),(19,12,NULL,19),(64,12,140,9),(14,12,800,9),(9,12,NULL,19),(24,12,NULL,19),(107,13,400,9),(6,13,1,15),(18,13,2,15),(19,13,4,5),(108,13,350,9),(109,13,3,6),(110,13,1.5,7),(13,13,125,9),(28,13,100,9),(38,13,2,5),(9,13,NULL,19),(10,13,NULL,19),(111,13,0.5,6),(112,13,1,12),(104,14,2,6),(113,14,1,5),(9,14,0.5,3),(77,14,1,5),(114,14,1.5,6),(115,14,1,6),(116,14,2,5),(117,14,1,6),(4,14,2,6),(2,15,6,6),(68,15,15,9),(9,15,3,9),(13,15,120,9),(1,15,0.6,6),(4,15,1.5,6),(11,15,5,6),(78,15,250,9),(118,15,150,9),(122,16,6,2),(121,16,3,13),(1,16,1,4),(41,16,1,4),(120,16,NULL,19),(119,16,NULL,19),(123,17,1,6),(124,17,1,6),(127,17,2,6),(104,17,0.5,6),(125,17,1,5),(126,17,0.5,4),(3,18,8,15),(128,18,8,5),(129,18,4,15),(130,18,12,15),(131,18,2,6),(132,18,NULL,19),(133,18,NULL,19),(104,19,1.5,6),(3,19,1,15),(134,19,0.5,4),(77,19,2,5),(13,19,2,5),(9,19,0.5,3),(135,19,2,6),(127,19,NULL,19),(79,20,25,9),(41,20,5,6),(4,20,1,6),(9,20,1,4),(77,20,1,5),(136,20,5,6),(2,20,8,6),(140,21,10,15),(60,21,4,15),(137,21,4,5),(138,21,1,6),(139,21,500,9),(140,21,10,15),(141,21,2.5,6),(142,21,NULL,19),(26,2,250,9),(3,22,5,15),(1,22,4,6),(13,22,150,9),(2,22,1.5,6),(142,22,0.75,6),(75,22,1,5),(11,22,4.15,6),(9,22,0.75,4),(143,22,700,9),(60,22,1,15),(144,22,2,6),(2,23,250,9),(137,23,350,9),(13,23,200,9),(60,23,1,15),(9,23,NULL,19),(145,23,4,15),(74,23,6,15),(3,23,360,9),(76,23,150,9),(1,23,250,9),(41,23,50,9),(3,24,3,15),(1,24,1.5,6),(146,24,0.75,6),(142,24,2,5),(68,24,1,4),(13,24,100,9),(137,24,2,6),(75,24,2,4),(60,24,1,15),(147,25,1,15),(148,25,3,6),(3,25,3,15),(149,25,50,9),(150,25,150,9),(9,25,0.5,4),(10,25,NULL,19),(151,25,130,9),(112,25,NULL,19),(49,25,NULL,19),(128,25,0.5,6),(1,25,0.5,6),(41,25,1,6),(93,26,500,9),(152,26,2,15),(32,26,2,15),(18,26,2,15),(87,26,1,15),(38,26,2,5),(20,26,1,5),(41,26,5,6),(153,26,3,5),(109,26,2,6),(11,26,3,6),(67,26,1,5),(154,26,0.5,4),(9,26,0.5,4),(112,26,1,12),(74,26,1,15),(155,27,200,9),(13,27,NULL,19),(63,27,NULL,19),(152,27,1,5),(11,27,0.5,6),(9,27,NULL,19),(10,27,NULL,19),(59,27,4,18),(28,27,NULL,19),(35,27,NULL,19),(48,28,1,10),(156,28,2,15),(49,28,2,15),(18,28,4,15),(41,28,6,6),(110,28,2,15),(157,28,1,11),(83,28,0.5,17),(9,28,NULL,19),(10,28,NULL,19),(54,29,3,2),(158,29,3,2),(141,29,5,2),(54,30,4,2),(159,30,2,2),(160,30,1,2),(161,30,4,2),(162,30,NULL,19),(48,121,8,18),(184,121,5,15),(50,121,8,12),(111,126,0,13),(9,127,0,19);
/*!40000 ALTER TABLE `r_recipe_ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_recipe_tag`
--

DROP TABLE IF EXISTS `r_recipe_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `r_recipe_tag` (
  `recipe_id` int NOT NULL,
  `tag_id` int NOT NULL,
  `owner` int DEFAULT NULL,
  KEY `fk_r_recipe_tag_1_idx` (`recipe_id`),
  KEY `fk_r_recipe_tag_2_idx` (`tag_id`),
  KEY `owner_id_tag` (`owner`),
  CONSTRAINT `owner_id_tag` FOREIGN KEY (`owner`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `recipe_id_tag` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `tag_id_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_recipe_tag`
--

LOCK TABLES `r_recipe_tag` WRITE;
/*!40000 ALTER TABLE `r_recipe_tag` DISABLE KEYS */;
INSERT INTO `r_recipe_tag` VALUES (3,6,NULL),(1,12,NULL),(2,12,NULL),(2,2,NULL),(5,12,NULL),(6,12,NULL),(7,16,NULL),(8,12,NULL),(8,2,NULL),(9,12,NULL),(9,2,NULL),(10,10,NULL),(11,12,NULL),(11,9,NULL),(12,12,NULL),(12,9,NULL),(13,12,NULL),(13,2,NULL),(14,16,NULL),(15,16,NULL),(16,15,NULL),(16,6,NULL),(16,9,NULL),(16,10,NULL),(17,16,NULL),(18,16,NULL),(19,16,NULL),(20,16,NULL),(21,13,NULL),(21,14,NULL),(22,13,NULL),(22,14,NULL),(23,13,NULL),(23,14,NULL),(24,14,NULL),(25,11,NULL),(26,11,NULL),(27,11,NULL),(27,6,NULL),(28,11,NULL),(28,2,NULL),(28,6,NULL),(29,15,NULL),(29,6,NULL),(29,10,NULL),(29,1,NULL),(30,15,NULL),(30,6,NULL),(30,10,NULL),(30,1,NULL),(26,3,NULL),(3,3,NULL),(3,12,NULL),(4,2,NULL),(4,3,NULL),(4,12,NULL),(121,57,55),(121,10,NULL),(121,1,NULL),(121,58,36),(5,36,36),(5,50,36),(126,3,36),(127,3,36),(127,3,NULL);
/*!40000 ALTER TABLE `r_recipe_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_shoppinglist_ingredient`
--

DROP TABLE IF EXISTS `r_shoppinglist_ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `r_shoppinglist_ingredient` (
  `shoppinglist_id` int DEFAULT NULL,
  `ingredient_id` int DEFAULT NULL,
  `qty` int DEFAULT NULL,
  `unit` varchar(45) DEFAULT NULL,
  KEY `fk_r_shoppinglist_ingredient_1_idx` (`shoppinglist_id`),
  KEY `fk_r_shoppinglist_ingredient_2_idx` (`ingredient_id`),
  CONSTRAINT `fk_r_shoppinglist_ingredient_1` FOREIGN KEY (`shoppinglist_id`) REFERENCES `shoppinglist` (`id`),
  CONSTRAINT `fk_r_shoppinglist_ingredient_2` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_shoppinglist_ingredient`
--

LOCK TABLES `r_shoppinglist_ingredient` WRITE;
/*!40000 ALTER TABLE `r_shoppinglist_ingredient` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_shoppinglist_ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_user_comment_recipe`
--

DROP TABLE IF EXISTS `r_user_comment_recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `r_user_comment_recipe` (
  `user_id` int DEFAULT NULL,
  `recipe_id` int DEFAULT NULL,
  `text` text,
  `date` mediumtext,
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `updatedDate` mediumtext,
  PRIMARY KEY (`id`),
  KEY `fk_r_user_comment_recipe_1_idx` (`user_id`),
  KEY `fk_r_user_comment_recipe_2_idx` (`recipe_id`),
  CONSTRAINT `fk_r_user_comment_recipe_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_r_user_comment_recipe_2` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_user_comment_recipe`
--

LOCK TABLES `r_user_comment_recipe` WRITE;
/*!40000 ALTER TABLE `r_user_comment_recipe` DISABLE KEYS */;
INSERT INTO `r_user_comment_recipe` VALUES (46,4,'This is gooooood food','1715629047',1,'1715629047'),(16,4,'Three eggs, two cups of flour, and a pinch of salt.','1715629099',2,'1715629710'),(46,2,'Denna är så jävla god asså!!','1715717497',25,'1715717497'),(34,1,'I made this but I changed the Yellow Onion for Saurkraut and it was awful! Worst recipe ever!!!','1715761369',28,'1715761515'),(46,10,'This is a good dish','1715766604',43,'1715766604'),(46,10,'This is a good dish!!','1715766608',44,'1715766608'),(46,10,'Very good','1715766611',45,'1715766611'),(46,10,'Very good!!!','1715766616',46,'1715766616'),(46,30,'Omg, this drink is SOOO delicious!','1715786072',63,'1715786072'),(46,7,'Yes, yes yes!!!! So not good....','1715786098',64,'1715786098'),(16,3,'Really enjoyed making this recipe!','1718208000',68,'1718208000'),(34,3,'The steps were clear and easy to follow. Delicious!','1718208000',69,'1718208000'),(35,3,'Had to adjust the seasoning a bit, but turned out great.','1718208000',70,'1718208000'),(36,3,'Hate it! Will definitely not make this again.','1718208000',71,'1715849886'),(37,3,'My family enjoyed this recipe a lot. Thanks for sharing!','1718208000',72,'1718208000'),(40,3,'I found this recipe to be a bit challenging for beginners!!','1718208000',74,'1716216220'),(37,3,'Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.','1715846884',78,'1715846884'),(38,9,'Spaghetti Carbonara has always been a personal favourite of mine. And this recipe is delightful! ','1715854625',79,'1715854625'),(38,16,'Made it with Glennfiddich. Love it. ','1715854819',80,'1715854819'),(38,7,'Very fluffy! I served with strawberryjam. But could not figure out what I should do with the iceberg lettuce. But It is always good to even out the guilty pleasures with a healthy salad I guess. :)','1715881746',95,'1715881965'),(38,14,'These oats had me fired up all day! ','1716043425',101,'1716043425'),(46,3,'This!','1716233727',104,'1716233727'),(46,3,'Now','1716235303',105,'1716235303'),(46,3,'This is not good now when i reconsider! To little salt','1716288120',106,'1716288120'),(36,2,'Wow','1716370159',108,'1716370159');
/*!40000 ALTER TABLE `r_user_comment_recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_user_dailydinnerlist_recipe`
--

DROP TABLE IF EXISTS `r_user_dailydinnerlist_recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `r_user_dailydinnerlist_recipe` (
  `user_id` int DEFAULT NULL,
  `recipe_id` int DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  KEY `fk_r_user_dailydinnerlist_recipe_1_idx` (`user_id`),
  KEY `fk_r_user_dailydinnerlist_recipe_2_idx` (`recipe_id`),
  CONSTRAINT `fk_r_user_dailydinnerlist_recipe_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_r_user_dailydinnerlist_recipe_2` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_user_dailydinnerlist_recipe`
--

LOCK TABLES `r_user_dailydinnerlist_recipe` WRITE;
/*!40000 ALTER TABLE `r_user_dailydinnerlist_recipe` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_user_dailydinnerlist_recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_user_favourite_recipe`
--

DROP TABLE IF EXISTS `r_user_favourite_recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `r_user_favourite_recipe` (
  `user_id` int DEFAULT NULL,
  `recipe_id` int DEFAULT NULL,
  UNIQUE KEY `unique_index` (`user_id`,`recipe_id`),
  KEY `fk_r_user_favourite_recipe_1_idx` (`user_id`),
  KEY `fk_r_user_favourite_recipe_2_idx` (`recipe_id`),
  CONSTRAINT `fk_r_user_favourite_recipe_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_r_user_favourite_recipe_2` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_user_favourite_recipe`
--

LOCK TABLES `r_user_favourite_recipe` WRITE;
/*!40000 ALTER TABLE `r_user_favourite_recipe` DISABLE KEYS */;
INSERT INTO `r_user_favourite_recipe` VALUES (36,1),(36,2),(36,5),(36,12),(36,17),(36,20),(36,28),(37,1),(37,2),(37,3),(37,4),(37,5),(37,7),(37,8),(37,9),(37,15),(37,26),(38,1),(38,3),(38,5),(38,7),(40,3),(40,17),(46,1),(46,2),(46,3),(46,7),(46,10),(46,13),(46,14),(46,16),(46,28),(46,30),(55,3);
/*!40000 ALTER TABLE `r_user_favourite_recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_user_shared_recipe`
--

DROP TABLE IF EXISTS `r_user_shared_recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `r_user_shared_recipe` (
  `user_id` int DEFAULT NULL,
  `recipe_id` int DEFAULT NULL,
  KEY `fk_r_user_shared_recipe_1_idx` (`user_id`),
  KEY `fk_r_user_shared_recipe_2_idx` (`recipe_id`),
  CONSTRAINT `fk_r_user_shared_recipe_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_r_user_shared_recipe_2` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_user_shared_recipe`
--

LOCK TABLES `r_user_shared_recipe` WRITE;
/*!40000 ALTER TABLE `r_user_shared_recipe` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_user_shared_recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `owner` int DEFAULT NULL,
  `portions` int NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_recipe_1_idx` (`owner`),
  CONSTRAINT `fk_recipe_1` FOREIGN KEY (`owner`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe`
--

LOCK TABLES `recipe` WRITE;
/*!40000 ALTER TABLE `recipe` DISABLE KEYS */;
INSERT INTO `recipe` VALUES (1,'Meatballs with Mashed Potatoes and Lingon Berries',NULL,4,'Savory meatballs nestled on a bed of creamy mashed potatoes, topped with a tangy lingonberry sauce. A symphony of flavors in every bite, blending hearty meatiness with comforting potatoes and a hint of sweet tartness.'),(2,'Lasagne',NULL,4,'Layers of lasagna noodles enveloped in rich marinara sauce, velvety béchamel, and a medley of cheeses, baked until golden. A classic comfort food that works just as well for sunday dinners or leftover-lunches!'),(3,'Malmöfalafel',NULL,4,'Crispy falafel patties wrapped in liba bread, topped with fresh veggies and drizzled with mild and garlic sauce. A Middle Eastern delight, each bite bursts with savory flavors and satisfying crunch.'),(4,'Penne alla Vodka',NULL,4,'Al dente penne pasta enveloped in a luscious vodka-infused tomato cream sauce, garnished with grated Parmesan. A tantalizing Italian creation, each forkful delivers a symphony of flavors.'),(5,'Club Sandwich',NULL,4,'Toasted bread loaded with bacon, turkey, tomatoes, lettuce, and mayo. A classic favorite, each bite offers a perfect balance of flavors and textures, satisfying every craving. A timeless classic.'),(6,'Fish and Chips',NULL,4,'Crispy battered fish served with golden fries. A classic delight, each bite offers a perfect blend of crunchy exterior and tender fish, paired with fluffy potatoes, creating a satisfying meal that\'s both comforting and delicious.'),(7,'Japanese Pancakes',NULL,12,'Fluffy Japanese pancakes, stacked high and served with a dollop of butter and a drizzle of maple syrup. Each bite is a delightful combination of airy texture and sweet indulgence, creating a heavenly breakfast or dessert experience.'),(8,'Napolitan Pizza with Proscuitto and Mozzarella',NULL,4,'Napolitan Pizza topped with savory prosciutto and melted mozzarella cheese on a thin, crispy crust. Each bite is a delicious harmony of flavors, blending the salty richness of prosciutto with the creamy goodness of mozzarella.'),(9,'Spaghetti Carbonara',NULL,4,'Spaghetti Carbonara, a classic Italian dish featuring al dente pasta tossed with creamy egg, crispy pancetta, Parmesan cheese, and black pepper. Each forkful is a perfect blend of richness and simplicity.'),(10,'Fish Soup with Green Vegetables',NULL,4,'A hearty fish soup brimming with tender chunks of fish and vibrant green vegetables. Each spoonful delivers a burst of flavors, marrying the freshness of the sea with the wholesome goodness of vegetables.'),(11,'Cashew Chicken Wok',NULL,4,'Cashew chicken wok: succulent chicken, crunchy cashews, crisp vegetables, all tossed in a savory sauce and stir-fried for a delightful meal bursting with taste and texture.'),(12,'Meatloaf and Potatoes',NULL,8,'Meatloaf, wrapped in bacon and seasoned with fragrant rosemary, accompanied by oven-roasted potatoes, crafting a hearty and satisfying meal.'),(13,'Risotto with Chanterelles, Almonds and Beurre noisette',NULL,4,'Creamy risotto infused with earthy chanterelles, toasted almonds, and rich beurre noisette'),(14,'Overnight Oats with Coffe and Cacao Nibs',NULL,2,'A delight for the truly morning tired! Creamy overnight oats with crunchy cocoa nibs prepared the night before. Topped with cold brew and frothed milk in the morning!'),(15,'Scones',NULL,8,'Scones are a must when the weekend comes around! Serve them with homemade queen jam and clotted cream for an extra luxurious touch.'),(16,'Old Fashioned',NULL,1,'The old fashioned, classic whiskey cocktail originated in the 1700s. Its simplicity, comprising whiskey, sugar, and bitters, allows for variations.This serves as a base recipe that can be adjusted according to taste.'),(17,'Smoothie with Peanutbutter',NULL,2,'This hearty smoothie with spinach, blueberries, quark, and peanut butter is perfect any time of the day!'),(18,'Eggs Benedict-Burger',NULL,4,'The ultimate breakfast, or why not as a luxurious brunch? Here, we transform the classic Eggs Benedict into a delightful burger!'),(19,'Breakfast Pie',NULL,1,'This is perhaps the nicest variation of the classic oatmeal: a hearty and delicious pie. It could also be the perfect first step when you want to introduce a child to oatmeal. The same nutrition in a slightly fancier way!'),(20,'Bread Rolls',NULL,20,'This bread requires neither kneading nor careful shaping, making it very easy to bake. It just needs to be mixed by hand into a sticky batter and left to rise in the bowl. Very tasty with soup and perfect for the buffet.'),(21,'Tiramisu',NULL,6,'Tiramisu, a classic Italian dessert featuring layers of creamy mascarpone cheese, coffee-soaked ladyfinger biscuits, and a hint of Marsala wine.'),(22,'Salty Caramel Cheesecake on Sticky Chocolate Cake base',NULL,1,'Salty Caramel Cheesecake atop a Sticky Chocolate Cake base. Rich, creamy caramel cheesecake meets a decadent chocolate foundation for a perfect balance of sweet and salty flavors.'),(23,'Tarte au Citron',NULL,1,'Elegant Tarte au Citron. With its buttery pastry crust and tangy lemon filling, each bite is a delightful balance of sweetness and citrus zing, perfect for any occasion.'),(24,'Dream Cake',NULL,1,'Dream cake, or swiss roll with buttercream, a heavenly treat that combines fluffy sponge cake with creamy, decadent buttercream frosting. Each bite is a perfect balance of lightness and richness.'),(25,'Västerbotten Squares with Bleak Roe',NULL,12,'Västerbotten squares with bleak roe. This Swedish delicacy features buttery, flaky pastry squares topped with a creamy Västerbotten cheese mixture and a generous dollop of briny bleak roe.'),(26,'Spicy Shrimp Soup',NULL,4,'Spicy Shrimp Soup, Senegalese heat and classic Swedish flavors. An easy recipe that works just as well for a weekday dinner as it does as an appetizer for New Year\'s.'),(27,'Mushroom Toast with Parmesan and Parsley',NULL,4,'A mushroom toast with that extra touch. Mushroom, parmesan, cream, and parsley are a truly fine combination. A splash of cream makes all the difference. The cream captures the delicious mushroom flavor that would otherwise stay in the pan.'),(28,'Italian Tomato Soup with Roasted Tomatoes',NULL,4,'A simple tomato soup where the vegetables gain delicious flavor and sweetness by first roasting them in the oven. With beans in the soup, it becomes substantial. Serve with bread if you want to make it more filling.'),(29,'Espresso Martini',NULL,1,'This classic cocktail combines vodka, simple syrup, and freshly brewed espresso for a sophisticated and energizing drink experience.'),(30,'Cosmopolitan',NULL,1,'This iconic drink blends vodka, cranberry juice, orange liqueur, and a splash of lime juice, creating a refreshing balance of sweet and tart flavors with a hint of citrus. Garnished with a twist of orange peel, it\'s the perfect cocktail for any occasion.'),(121,'Cucumber salad',36,2,'A great salad.'),(122,'Test recipe',36,14,'Trying to make a recipe.\n'),(123,'Test recipe',36,14,'Trying to make a recipe.\n'),(124,'Test recipe',36,14,'Trying to make a recipe.\n'),(125,'Test recipe',36,14,'Trying to make a recipe.\n'),(126,'Test recipe',36,14,'Trying to make a recipe.\n'),(127,'t',36,4,'t');
/*!40000 ALTER TABLE `recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoppinglist`
--

DROP TABLE IF EXISTS `shoppinglist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shoppinglist` (
  `id` int NOT NULL AUTO_INCREMENT,
  `owner` int NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_shoppinglist_1_idx` (`owner`),
  CONSTRAINT `fk_shoppinglist_1` FOREIGN KEY (`owner`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppinglist`
--

LOCK TABLES `shoppinglist` WRITE;
/*!40000 ALTER TABLE `shoppinglist` DISABLE KEYS */;
/*!40000 ALTER TABLE `shoppinglist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `step`
--

DROP TABLE IF EXISTS `step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `step` (
  `recipe_id` int NOT NULL,
  `step_index` int NOT NULL,
  `instruction` text NOT NULL,
  PRIMARY KEY (`recipe_id`,`step_index`),
  KEY `recipe_id_idx` (`recipe_id`),
  CONSTRAINT `recipe_id` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `step`
--

LOCK TABLES `step` WRITE;
/*!40000 ALTER TABLE `step` DISABLE KEYS */;
INSERT INTO `step` VALUES (1,1,'In a bowl, pour the breadcrumbs and pour the cream-milk (equal parts cream and milk, 1.5 dl total) over it to let it swell.'),(1,2,'Grate the onion using a grater and add it to the bowl. Proceed with syrup, mustard, eggs and salt. Mix it all together and taste, then add the ground meat and mix well.'),(1,3,'Peel and boil the potatoes.'),(1,4,'Prepare a bowl of water and keep your hands wet when rolling the meatballs, choose the size according to your preference.'),(1,5,'Fry the meatballs in butter over high heat first, then lower the heat. Let the meatballs cook through, and cut one in half to check if they are fully cooked.'),(1,6,'Drain the potatoes, add the milk (1.5 dl), and let it come to a boil in the pot before adding the butter. Crush the potatoes with an electric mixer, then whip the mash to the desired consistency. Season with salt to taste.'),(1,7,'Serve the meatballs with the mash and lingonberries.'),(2,1,'Preheat the oven to 200 degrees Celsius.'),(2,2,'Sauté the chopped vegetables in olive oil until soft. Add the ground meat and let it brown slightly.'),(2,3,'Add the tomato paste and sauté for 1 minute. Add the wine and let it simmer for 1 minute before adding the tomatoes, thyme, rosemary, and bay leaves. Season with salt and pepper and let it simmer while you prepare the bechamel sauce.'),(2,4,'Melt the butter in a large saucepan. Whisk in the flour and let it cook for a short while in the butter, this removes the \"floury taste\" from the sauce. Gradually whisk in the milk, and let it gently come to a boil while stirring constantly. Remove from heat and stir in both grated cheeses. Stir until all the cheese has melted. Season with nutmeg, salt, and white pepper to taste.'),(2,5,'Layer the meat sauce, lasagna sheets, and bechamel sauce in a baking dish, ending with a layer of bechamel sauce.'),(2,6,'Bake in the oven for 20-25 minutes. Let it stand for 5-10 minutes before slicing the lasagna.'),(2,7,'Arrange arugula and tomatoes in a bowl. Drizzle with olive oil and balsamic vinegar, season with salt and pepper.'),(2,8,'Serve your classic lasagna with the simple salad.'),(3,1,'Soak the chickpeas for 12 hours. Rinse well.'),(3,2,'Finely grind the chickpeas in a food processor. Do it in batches. Grind it finely so that it holds together when you squeeze the mass in your hand. You can also grind them in a meat grinder. Pour into a bowl.'),(3,3,'Mix in yellow onion, garlic, parsley, coriander, cumin, salt (0.5 tsk), and pepper.'),(3,4,'Squeeze a little chickpea mixture in your hand. It should hold together.'),(3,5,'Heat the oil (2 l) to 180 degrees Celsius in a thick-bottomed saucepan or deep fryer.'),(3,6,'Shape balls of the chickpea mixture using an ice cream scoop or falafel press. Press them together to hold.'),(3,7,'Drop a few balls at a time into the hot oil and fry until they float to the surface and are nicely golden brown.'),(3,8,'Lift out with a slotted spoon and let drain on some paper towels. Salt.'),(3,9,'Mix all the ingredients in a bowl.'),(3,10,'Blend chickpea broth, garlic, lemon juice, and salt (0.5 tsk) into a fluffy puree using an immersion blender.'),(3,11,'Now gradually blend in some oil (2 dl) until you get a smooth, fluffy mayonnaise.'),(3,12,'Lay out a piece of flatbread and spread a layer of toum in the middle. Place 4-5 falafels in the middle and lightly press them.'),(3,13,'Drizzle over mild sauce and hot sauce.'),(3,14,'Add some of each vegetable. If you like pepperoncini and fried eggplant (see tips below), you can add those too. I can\'t do without them.'),(3,15,'Roll up the wrap and wrap it in wax paper or aluminum foil. Eat it right away!'),(3,16,'I always ask for fried eggplant in my falafel (if they have it, of course). Halve the eggplant lengthwise and slice into half moons. Salt and let sit for 30 minutes. Press with paper towels to absorb the liquid and then fry the eggplant until golden in plenty of oil. Salt.'),(4,1,'Peel and slice the garlic.'),(4,2,'Fry the pancetta until crispy in 1 tablespoon of olive oil in a frying pan or pot. Add the garlic and continue to sauté for 1–2 minutes. Pour in the vodka and let it simmer for a couple of minutes. Stir in the tomatoes. Lightly crush them with a spoon. Add the cream and TABASCO® Original hot sauce. Bring to a boil and simmer for about 10 minutes covered until a creamy tomato sauce forms.'),(4,3,'Cook the pasta for about 2 minutes less than indicated on the package. Drain, but save 1 dl of pasta water.'),(4,4,'Stir the pasta into the sauce. Thin with pasta water and let everything become hot. Season with salt and pepper to taste.'),(4,5,'Serve with grated parmesan or pecorino cheese and more TABASCO® Original hot sauce to sprinkle over for those who desire even more heat.'),(5,1,'Preheat the oven to 125 degrees Celsius.'),(5,2,'Season the chicken with salt and brown it in a frying pan with oil and garlic. Make sure the skin becomes crispy. Continue cooking the chicken in the oven until it is fully cooked through (70 degrees Celsius internal temperature). Let it rest before carving.'),(5,3,'Preheat the oven to 250 degrees Celsius.'),(5,4,'Brush both sides of the bread with oil and garlic. Lightly salt. Roast in the oven for 5-6 minutes until they are golden brown. Let them cool.'),(5,5,'Lay out the bacon on a baking sheet lined with parchment paper and cook for 5-6 minutes in the oven at 250 degrees Celsius. Let it cool.'),(5,6,'Mix egg yolk, vinegar, and mustard. Whisk in the oil gently, starting with a few drops at a time and then pouring in a thin stream while continuing to whisk. Season with salt to taste.'),(5,7,'Distribute all the ingredients between the bread, cut and secure with a wooden pick.'),(6,1,'Peel and julienne the potatoes. Place them in a bowl and pour hot water over them to cover. Add the lemon juice and let sit for 10 minutes to remove the starch. Drain well and pat the strips dry with a towel.'),(6,2,'Heat the oil (2 l) to 140°C in a large saucepan or deep fryer (have a lid on hand to cover the saucepan in case the oil catches fire). Add half of the potatoes and fry for 4–6 minutes until they begin to soften and turn slightly golden. Drain on paper towels. Fry the rest, drain, and refrigerate the potatoes.'),(6,3,'Mix flour, cornstarch, and baking powder for the fry batter in a bowl. Add beer and lemon juice and quickly stir until smooth. Season with salt and pepper and let the batter rest for 20 minutes at room temperature.'),(6,4,'Ensure all ingredients for the remoulade sauce are at the same temperature to prevent it from curdling. Mix egg yolks, mustard, and vinegar in a bowl. Gradually add the oil (3 dl), first drop by drop and then in a thin stream while whisking vigorously until the mixture has the consistency of mayonnaise. Season with salt and pepper. (With an immersion blender, you can blend all the ingredients in a tall container, insert the blender and start from the bottom, slowly pulling it up through the mixture. Move it up and down in the bowl until the sauce comes together. Season with salt and pepper.)'),(6,5,'Chop the pickles and mix with the sauce.'),(6,6,'Cut the fish fillets into long, uniform pieces.'),(6,7,'Heat the rapeseed oil to 160°C.'),(6,8,'Sprinkle 1⁄2 dl of flour (extra) on a plate and mix with salt and pepper. Coat the fish pieces in the flour and dip them in the fry batter. Carefully place them in the oil and fry until golden brown, about 5 minutes. Do not fry all pieces at once as it will cool down the oil.'),(6,9,'Just before serving: Heat the oil again, now to 180°C, and fry half of the potatoes again until golden brown and crispy, 2–4 minutes. Drain on paper towels. Fry the rest and drain. Lightly salt before serving.'),(6,10,'Serve the fish with the remoulade sauce, French fries, and accompaniments.'),(7,1,'Whisk egg yolks, milk, and oil until fluffy in a bowl using an immersion blender.'),(7,2,'Sift in flour and vanilla sugar and mix for an additional 1–2 minutes.'),(7,3,'In another bowl, preferably glass or metal, whisk the egg whites until stiff peaks form using an electric mixer. Gradually add the sugar, continuing to whisk until you get a firm and glossy meringue mixture.'),(7,4,'Gently fold the meringue into the egg yolk mixture in four batches using a spatula – work gently and try to retain as much air in the batter as possible.'),(7,5,'Melt some butter over low heat in a non-stick pan. Pipe or spoon small, tall pancakes onto the pan (about ½ deciliter per pancake) and cook for about 4 minutes on each side. Flip them carefully as they are very delicate. When cooking the second side, you can cover them with a lid to ensure both sides of the pancakes are cooked.'),(7,6,'Serve the pancakes with butter, honey, and fresh berries.'),(8,1,'Crumble the yeast into the bowl of a stand mixer. Add water and dissolve the yeast. Then add flour and mix on low speed for about 5 minutes. Add salt and olive oil and mix for an additional 5 minutes at medium speed.'),(8,2,'Divide the dough into four equal parts and roll them into smooth balls. Rub the dough with olive oil and let it rise for 12–24 hours in the refrigerator, or alternatively, for 4 hours at room temperature.'),(8,3,'Preheat the oven to 250 degrees Celsius (or the highest possible temperature) and place a pizza stone inside to heat up.'),(8,4,'Stretch out the dough with your hands to make them thin in the middle with thicker edges.'),(8,5,'Crush the tomatoes with a fork and spread a layer on the pizzas. Drizzle with a little olive oil and crumble the mozzarella over the top.'),(8,6,'Carefully transfer the pizza onto the hot stone and bake in the oven for about 10-15 minutes or until the pizza is nicely browned.'),(8,7,'Finish with a few basil leaves and serve immediately.'),(9,1,'Cook the spaghetti for about 3 minutes less than the instructions on the package indicate.'),(9,2,'Cut the pancetta into small pieces.'),(9,3,'Fry the pancetta in a wok pan or a skillet with high edges without fat until it becomes crispy. Remove and set aside. Leave the fat in the pan.'),(9,4,'Whisk together the egg yolks with the finely grated cheese and plenty of freshly ground black pepper in a bowl.'),(9,5,'Heat the pan with the pancetta fat again, pour in about 3 dl of pasta water, and let it boil.'),(9,6,'Drain the spaghetti and add it to the pan with the pancetta. Just as when cooking risotto in broth, the pasta should be cooked in the pancetta fat. Let it cook for about 2-3 minutes while stirring constantly; this allows the starch from the pasta to be released, creating a creamy sauce.'),(9,7,'When the water starts to boil down and there\'s only a tablespoon or so left, remove the pan from the heat. Add the egg mixture, stirring until it becomes creamy, adding a little water if it becomes dry. Season with salt and pepper to taste.'),(10,1,'Peel and finely chop the shallot and garlic. Finely chop the chili pepper.'),(10,2,'Sauté the shallot, chili, and ginger in oil in a pot for about a minute. Add water, stock, and cream. Let it simmer for 5 minutes.'),(10,3,'Cut the fish into bite-sized pieces. Break the broccoli into small florets.'),(10,4,'Add the fish, broccoli, and sugar snap peas to the pot and let it simmer for 3 minutes.'),(10,5,'Add the shrimp and frozen sugar snap peas, let everything heat through. Season with salt and black pepper. Sprinkle chopped parsley over the soup and serve.'),(11,1,'Toast the Sichuan peppercorns in a dry pan for a few minutes. Grind into a fine powder using a mortar and pestle.'),(11,2,'Mix together oyster sauce, chinese soy sauce, black rice vinegar, sugar and sesame oil for the sauce.'),(11,3,'Heat 0.5 dl of oil in a wok. Once hot, add the raw cashew nuts. Stir-fry until they are dark golden brown. Remove them from the wok and let them cool.'),(11,4,'Stir-fry garlic and ginger in the wok for 30 seconds, then add the chicken. Sauté until the chicken is cooked and nicely browned. Transfer to a plate and keep it warm.'),(11,5,'If needed, add more oil to the wok and stir-fry broccoli, carrot, and onion over high heat until they are slightly browned. Return the chicken and cashew nuts to the wok.'),(11,6,'Add the ground Sichuan peppercorns and optionally chili flakes, and stir-fry. Pour in the sauce and stir until everything is coated with the delicious flavors.'),(11,7,'Garnish with cilantro and serve this delicious stir-fry with steaming hot jasmine rice.'),(12,1,'Preheat the oven to 200 degrees Celsius.'),(12,2,'Mix oats, eggs, sour cream, ground meat, seasoning powder, and black pepper.'),(12,3,'Grease a spacious baking sheet or ovenproof dish with olive oil, place the meat mixture and shape it into a loaf.'),(12,4,'Cover the loaf with bacon slices.'),(12,5,'Arrange potatoes around the loaf, season with salt, pepper, add rosemary sprigs, and drizzle with olive oil.'),(12,6,'Bake in the middle of the oven for about 40 minutes.'),(13,1,'Fry the chanterelles over high heat in rapeseed oil until they are lightly browned. Season with salt and pepper.'),(13,2,'Bring the broth to a boil and keep it warm.'),(13,3,'In a small saucepan, combine the butter (75 g) for the topping and chopped almonds. Brown until the butter smells like caramel. Keep warm.'),(13,4,'Fry the onion in olive oil in a heavy-bottomed saucepan. Add the rice and stir in the saucepan for a minute. Pour in the wine and let it absorb into the rice.'),(13,5,'Add half of the chanterelles to the rice.'),(13,6,'Pour in hot broth, 1 dl at a time. Stir constantly and pour in more broth as it is absorbed by the rice. It\'s the stirring that makes the risotto creamy. Continue until all the broth is in the risotto and the risotto is creamy and slightly loose in texture, about 20 minutes.'),(13,7,'Remove the saucepan from the heat and add butter (50 g) and parmesan cheese, then stir.'),(13,8,'Serve the risotto on plates. Top with the remaining chanterelles, the browned almond butter, and a little dill.'),(14,1,'Mix all ingredients for the overnight oats together. Divide into two jars or glasses and refrigerate for at least 2 hours or overnight.'),(14,2,'Froth milk in a saucepan by hand. Top the oatmeal with cold brew and frothed milk.'),(15,1,'Preheat the oven to 220 degrees Celsius.'),(15,2,'Measure out flour, baking powder, and salt in a bowl.'),(15,3,'Add the butter and crumble the dough. The dough should be crumbly, the more you work it, the drier it becomes!'),(15,4,'Add the sugar.'),(15,5,'Pour in the milk and stir with a wooden spoon.'),(15,6,'Transfer the dough onto a floured work surface.'),(15,7,'Roll out the dough to 3 cm thick.'),(15,8,'Cut out 8 scones using a glass and place them on a baking sheet lined with parchment paper.'),(15,9,'Bake the scones for about 12 minutes until they are golden brown. Let them cool on a wire rack.'),(15,10,'Reduce the whipping cream by half.'),(15,11,'Chill to 6 degrees and then whip it up.'),(15,12,'Cook berries and sugar together for about 10 minutes. Then blend the jam until smooth.\"'),(16,1,'Add the sugar and bitters into a mixing glass, then add the water, and stir until the sugar is nearly dissolved. '),(16,2,'Fill the mixing glass with ice, add the bourbon, and stir until well-chilled. '),(16,3,'Strain into a rocks glass over one large ice cube. '),(16,4,'Express the oil of an orange twist over the glass, then drop into the glass to garnish.'),(17,1,'Blend the ingredients in a bowl until smooth.'),(17,2,'Pour the smoothie into a bowl.'),(17,3,'Top with your choice of toppings, such as pumpkin seeds, almond flakes, granola, or cereal.'),(18,1,'Bring water to a boil in a spacious saucepan. Add white vinegar.'),(18,2,'Turn off the heat.'),(18,3,'Create a whirlpool in the water using a spoon. Crack an egg into a cup and lower the cup into the saucepan so the egg spins along in the whirlpool.'),(18,4,'Let it cook for about 3 minutes.'),(18,5,'Then lift the egg out with a slotted spoon and place it on a flat plate. Repeat with all eggs.'),(18,6,'Toast the hamburger buns until they are golden and crispy.'),(18,7,'Assemble the burger with 3 slices of ham, a dollop of hollandaise sauce, two eggs, and more hollandaise sauce. Garnish with paprika powder and chives.'),(19,1,'Preheat the oven to 200 degrees Celsius.'),(19,2,'Mix all the ingredients for the crumble topping.'),(19,3,'Spread the berries in the bottom of a small dish, 10–15 cm in diameter.'),(19,4,'Evenly distribute the crumble topping over the filling and bake the pie in the middle of the oven for 15–20 minutes.'),(19,5,'Serve with vanilla quark or something else you think pairs well.'),(20,1,'Crumble the yeast into a bowl.'),(20,2,'Mix water and milk and pour a little over the yeast.'),(20,3,'Stir until the yeast dissolves.'),(20,4,'Add the rest of the water-milk mixture, salt, and honey.'),(20,5,'Stir in whole wheat flour and most of the wheat flour (save about 1 dl for rolling out).'),(20,6,'Quickly work the loose dough together.'),(20,7,'Sprinkle a little wheat flour over it, cover with a cloth, and let the dough rise for 1–1 ½ hours. It should only rise once.'),(20,8,'Sprinkle about ½ dl of wheat flour on the work surface and carefully tip out the loose dough. Note! Do not knead - the yeast bubbles should remain.'),(20,9,'Sprinkle a little more wheat flour over the dough, about ½ dl.'),(20,10,'Gently and lightly flatten the dough by hand to about 30 x 40 cm.'),(20,11,'Quickly cut the dough into 6–7 cm narrow strips. It\'s easiest with a metal dough scraper.'),(20,12,'Divide each strip into 2–3 pieces diagonally. It doesn\'t matter if the breads don\'t have the same shape, as long as they are roughly the same size.'),(20,13,'Fold and place the breads (they are still loose) on baking sheets with parchment paper or directly on perforated sheets.'),(20,14,'Bake immediately in intervals at 230º for 5 minutes once the oven is hot.'),(20,15,'Change to \"hot air\" at 230˚ and continue baking for another 4–5 minutes until the breads are nicely light brown.'),(20,16,'Let cool on a wire rack without a cloth, so the bread crust becomes wonderfully crispy.'),(21,1,'Whisk the egg yolks and powdered sugar until fluffy and porous.'),(21,2,'Mix the mascarpone cheese and Marsala wine.'),(21,3,'Fold together with the egg mixture.'),(21,4,'Whip the egg whites until stiff peaks form and gently fold them in.'),(21,5,'Quickly dip halved ladyfinger biscuits in coffee.'),(21,6,'Layer the mixture with biscuits in a large baking dish or individual glasses.'),(21,7,'Dust the Tiramisu with cocoa powder before serving.'),(22,1,'Grease the edges of a 24 cm diameter round springform pan with butter and line the bottom with parchment paper. Melt the butter (100 g) in a saucepan and let it cool slightly. Mix together the eggs (2 pcs) and sugar (1 dl). Combine the flour (1 dl), cocoa, and vanilla sugar. Sift into the egg mixture and mix. Pour in the melted butter and stir with a spatula until smooth. Pour the batter into the springform pan and try to distribute it as evenly as possible. Set aside.'),(22,2,'Heat the sugar (2 dl) over medium-high heat in a saucepan. Stir constantly until all the sugar has melted (the sugar will harden and turn into small crystals, but it will melt, just make sure it doesn\'t burn). Add the butter (50 g) and let everything come together. Pour in the cream (1 dl) and let it boil while stirring for about 1-2 minutes or until it has a lovely thick, flowing consistency and a nice color. Add salt and stir. Let cool for a while.'),(22,3,'Preheat the oven to 175 degrees Celsius. Whisk together cream cheese (600 g), eggs (3 pcs), egg yolk, sugar (1 dl), flour (0.5 dl), and salty caramel (from step 2, save about 1 dl) until smooth and pour the mixture into the pan over the unbaked sticky chocolate cake. Add some salty caramel (the saved portion) and bake in the middle of the oven for about 45-50 minutes or until the cheesecake feels quite firm but still not completely set. Let cool completely.'),(22,4,'Whip together cream cheese (100 g) and dulce de leche (1 dl). Add cream (3 dl) and whip until fluffy. Not too hard but pipable.'),(22,5,'Heat up dulce de leche (1 dl) with cream (1 msk) and stir. Release the springform pan and pipe the dulce de leche fluff over the completely cooled cheesecake. Crumble some small pretzels or salted sticks and drizzle over the sauce.'),(23,1,'Mix flour, powdered sugar (100 g), butter (100 g), egg yolk and salt quickly. Let chill for a while.'),(23,2,'Roll out the dough and line a pie dish.'),(23,3,'Cover the dough with foil filled with beans/sugar to keep it in place during baking.'),(23,4,'Bake the base at 180 degrees Celsius for about 10-15 minutes. Remove and discard the foil.'),(23,5,'Soak the gelatin in cold water.'),(23,6,'Mix lemon juice and zest, eggs, and powdered sugar (250 g) in a saucepan. Bring to a simmer and cook for 5 minutes, stirring constantly.'),(23,7,'Squeeze excess water from the gelatin sheets and add to the mixture along with the butter (100 g). Stir and let cool. Strain the lemon curd and fill the baked pie crust with it. Let cool in the fridge.'),(23,8,'Boil sugar and water. When the syrup reaches 110 degrees Celsius, start whisking the egg whites for meringue.'),(23,9,'When the syrup reaches 122 degrees Celsius, pour it in a fine stream into the whipped egg whites. Whisk until the meringue is cold/room temperature.'),(23,10,'Decorate the lemon pie with meringue and grill.'),(24,1,'Preheat the oven to 250 degrees Celsius. Beat eggs and sugar until fluffy.'),(24,2,'Mix potato flour, sifted cocoa, and baking powder.'),(24,3,'Fold the mixture into the batter. Spread the batter onto a baking sheet lined with parchment paper in a 30 x 40 cm pan.'),(24,4,'Bake in the middle of the oven for about 5 minutes.'),(24,5,'Flip the cake onto sugared paper. Remove the paper the cake was baked on. Let it cool.'),(24,6,'Beat butter and powdered sugar until creamy for the filling.'),(24,7,'Add vanilla sugar and stir in egg yolk.'),(24,8,'Spread the filling on the cake and roll it up.'),(24,9,'Let the Swiss roll cake chill for a while before slicing.'),(25,1,'Preheat the oven to 200 degrees Celsius.'),(25,2,'Lay out the puff pastry along the edges of a 20 x 30 cm baking dish, ensuring it covers the entire base and edges. Trim any excess pastry that hangs over the edges.'),(25,3,'Prick the bottom with a fork and pre-bake the puff pastry base for 10 minutes.'),(25,4,'Mix together the ingredients for the filling: crème fraiche, eggs, Västerbotten and Präst cheese, salt, and black pepper.'),(25,5,'Once the puff pastry has pre-baked, remove the dish from the oven and reduce the oven temperature to 175 degrees Celsius.'),(25,6,'Fill the pastry shell with the filling mixture and return to the oven for about 18 minutes, until the filling has set and turned golden.'),(25,7,'Allow to cool completely, then cut into desired-sized squares. Top with crème fraiche, bleak roe, dill, and pickled red onion or other toppings of your choice!'),(25,8,'Measure vinegar, sugar, and water in a saucepan. Bring to a boil and remove from heat, allowing the sugar to completely dissolve.'),(25,9,'Thinly slice the red onion and place in a bowl. Pour the vinegar mixture over the onions and let marinate for at least a couple of hours, preferably overnight.'),(26,1,'Peel and grate the shallots and carrots using a grater. Peel and finely chop the garlic. Deseed and finely chop the chili.'),(26,2,'Sauté the onion, carrots, and garlic in oil in a saucepan until soft.'),(26,3,'Add the tomato puré and chili. Sauté for about 5 minutes.'),(26,4,'Add water and stock, then simmer covered for about 15 minutes, then bring the broth to a boil.'),(26,5,'educe the heat and add wine and cream. Dissolve cornstarch in a little water and stir into the soup. Let it simmer for about 5 minutes on low heat.'),(26,6,'Season with cayenne pepper and taste with salt.'),(26,7,'Serve the soup with the peeled shrimp, dill, and lemon slices.'),(27,1,'Fry the mushrooms in oil and butter in a frying pan until they have some color. Add onions and pour in cream. Let simmer for a minute. Season with salt and pepper.'),(27,2,'Fry the bread in butter and oil on both sides until it has a nice color and is crispy. Spread the mushrooms on the bread slices and sprinkle with parmesan cheese and parsley. Serve immediately.'),(28,1,'Preheat the oven to 200 degrees Celsius.'),(28,2,'Cut the tomatoes into large pieces. Halve, seed, and cut the bell pepper into large pieces. Quarter the red onion. Leave the skin on the garlic cloves.'),(28,3,'Place tomatoes, bell pepper, red onion, and garlic on a baking sheet. Drizzle with olive oil.'),(28,4,'Roast in the oven for about 30-40 minutes.'),(28,5,'Bring water and bouillon cubes to a boil in a large pot.'),(28,6,'Remove the vegetables from the oven. Remove the garlic cloves and peel. Transfer all the roasted vegetables (including any liquid) to the pot with the broth, along with half of the basil.'),(28,7,'Bring to a boil and blend smooth with an immersion blender. Season with salt and pepper.'),(28,8,'Drain and rinse the beans. Add the beans to the soup and let simmer for 5-8 minutes.'),(28,9,'Serve and garnish with basil and a drizzle of olive oil, if desired.'),(29,1,'Shake the ingredients for the drink cold with ice.'),(29,2,'Strain through a fine-mesh strainer into a stemmed glass.'),(29,3,'Garnish with 3 coffee beans.'),(30,1,'Shake with ice and strain into a chilled stemmed glass.'),(30,2,'Garnish with an orange zest.'),(121,1,'Slice the cucumber'),(121,2,'Add lettuce'),(122,1,'dssdasdadas'),(122,2,'xsaxasxa'),(122,3,'zsaxscva'),(122,4,'ghfgfddvf'),(122,5,'cdscsx'),(123,1,'dssdasdadas'),(123,2,'xsaxasxa'),(123,3,'zsaxscva'),(123,4,'ghfgfddvf'),(123,5,'cdscsx'),(124,1,'dssdasdadas'),(124,2,'xsaxasxa'),(124,3,'zsaxscva'),(124,4,'ghfgfddvf'),(124,5,'cdscsx'),(125,1,'dssdasdadas'),(125,2,'xsaxasxa'),(125,3,'zsaxscva'),(125,4,'ghfgfddvf'),(125,5,'cdscsx'),(126,1,'dssdasdadas'),(126,2,'xsaxasxa'),(126,3,'zsaxscva'),(126,4,'ghfgfddvf'),(126,5,'cdscsx'),(127,1,'dsadasdsadas'),(127,2,'xsaxa');
/*!40000 ALTER TABLE `step` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `owner` int DEFAULT NULL,
  `backgroundcolor` varchar(20) NOT NULL DEFAULT '#fdf6e3',
  `textcolor` varchar(20) NOT NULL DEFAULT '#000000',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `idx_name_owner` (`name`,`owner`),
  KEY `fk_tag_1_idx` (`owner`),
  CONSTRAINT `fk_tag_1` FOREIGN KEY (`owner`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'Vegan',NULL,'#fdf6e3','#000000'),(2,'Italian',NULL,'#fdf6e3','#000000'),(3,'Spicy',NULL,'#fdf6e3','#000000'),(4,'Mexican',NULL,'#fdf6e3','#000000'),(5,'Raw-food',NULL,'#fdf6e3','#000000'),(6,'Vegetarian',NULL,'#fdf6e3','#000000'),(7,'SadeksSpecial',40,'#00ffff','#336666'),(9,'Lactose Free',NULL,'#fdf6e3','#000000'),(10,'Gluten Free',NULL,'#fdf6e3','#000000'),(11,'Starter',NULL,'#fdf6e3','#000000'),(12,'Main Course',NULL,'#fdf6e3','#000000'),(13,'Dessert',NULL,'#fdf6e3','#000000'),(14,'Sweets',NULL,'#fdf6e3','#000000'),(15,'Cocktail',NULL,'#fdf6e3','#000000'),(16,'Breakfast',NULL,'#fdf6e3','#000000'),(20,'montag',38,'#ff00ff','#000000'),(21,'Hot Hot Hot!!!',38,'#ff6666','#000000'),(22,'PeterTag',38,'#6680e6','#111111'),(36,'Delicious',36,'#cc8099','#000000'),(37,'Beach2024',46,'#008080','#ff00ff'),(38,'Beach2025',46,'#ffcce6','#336666'),(39,'Mål i livet',46,'#331a80','#ffffff'),(40,'Dont eat',46,'#cc3333','#ffffff'),(50,'Must eat',36,'#ffccff','#800080'),(51,'Best ones',55,'#0000ff','#000000'),(53,'EliasTesttagg',37,'#ffff00','#000000'),(54,'EliasTesttagg2',37,'#00ff00','#000000'),(55,'EliasTesttagg3',37,'#00ffff','#336666'),(57,'Salad',55,'#008000','#000000'),(58,'Salad',36,'#008000','#000000');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit`
--

DROP TABLE IF EXISTS `unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit`
--

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
INSERT INTO `unit` VALUES (1,'ml'),(2,'cl'),(3,'krm'),(4,'tsk'),(5,'msk'),(6,'dl'),(7,'l'),(8,'mg'),(9,'g'),(10,'kg'),(11,'can'),(12,'bunch(es)'),(13,'dash(es)'),(14,'pack(s)'),(15,'pcs'),(16,'portion(s)'),(17,'pot(s)'),(18,'slice(s)'),(19,' ');
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `isadmin` tinyint NOT NULL DEFAULT '0',
  `displayname` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (13,'Admin','Adminsson',1,'admin','admin','0192023a7bbd73250516f069df18b500'),(16,'christian','testsson',0,'Gertrud','christiantest','7a519571b67279d9e76688779719cbcb'),(34,'Vilmer','Lennartsson',0,'vimler00','vimler','bfff961f7e9c91b6d5f87ac341a197e6'),(35,'Tobias','Gidlund',0,'General','GeneralGrievous','0192023a7bbd73250516f069df18b500'),(36,'Karl','Tuomisto',0,'kalle39','kalle37','9b45f696f421f9c982beb5b7ee725592'),(37,'Elias','Jaensson',0,'Jaensson','elias','16d7a4fca7442dda3ad93c9a726597e4'),(38,'p','p',0,'peter','peter','e3e7f312a36e128c29a42352bb4ff8d7'),(40,'s','a',0,'s.a','sadek','0a0942d0acad85babd8ffa65e0d7b555'),(46,'Andreas','Nilsson',0,'Bookmaker','isn','206c9fcc83c6c95d43c6a8a20ccb198c'),(47,'c','a',0,'Christian','Ca','c9470439fd674e043cb4fd130f716dd3'),(55,'Kalle','Tuomisto',0,'karl37','kalle38','9b45f696f421f9c982beb5b7ee725592');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-23 11:21:12
