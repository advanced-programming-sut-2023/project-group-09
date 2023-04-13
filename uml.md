classDiagram
direction BT
class ActivityPermissions {
<<enumeration>>
  + ActivityPermissions() 
  + valueOf(String) ActivityPermissions
  + values() ActivityPermissions[]
}
class Application {
  + Application() 
  - ArrayList~User~ users
  - User currentUser
  + getUserByUsername(String) User?
  + isUserExists(String) boolean
   ArrayList~User~ users
   User currentUser
}
class ArabianMercenary {
  + ArabianMercenary() 
}
class AttackRating {
<<enumeration>>
  - AttackRating(int) 
  - int rate
  + values() AttackRating[]
  + valueOf(String) AttackRating
   int rate
}
class AttackingAndDefendingTool {
  + AttackingAndDefendingTool() 
  - int x
  - ArrayList~Human~ engineers
  - Speed speed
  - int shootingRange
  - int numberOfRequiredEngineers
  - Government government
  - int y
  - ArrayList~Permission~ permissions
   int shootingRange
   ArrayList~Permission~ permissions
   int numberOfRequiredEngineers
   int y
   int x
   Government government
   Speed speed
   ArrayList~Human~ engineers
}
class Building {
  + Building() 
  - int numberOfRequiredEngineers
  - int startY
  - HashMap~String, Integer~ cost
  - String type
  - int hp
  - int numberOfRequiredWorkers
  - int endY
  - ArrayList~Human~ requiredHumans
  - int endX
  - ArrayList~Permission~ landPermissions
  - ArrayList~Permission~ activityPermissions
  - int startX
   ArrayList~Permission~ landPermissions
   int startY
   HashMap~String, Integer~ cost
   String type
   int numberOfRequiredWorkers
   int numberOfRequiredEngineers
   ArrayList~Permission~ activityPermissions
   ArrayList~Human~ requiredHumans
   int hp
   int endX
   int endY
   int startX
}
class BuildingMenu {
  + BuildingMenu() 
  + run(Scanner) void
}
class BuildingStates {
<<enumeration>>
  + BuildingStates() 
  + values() BuildingStates[]
  + valueOf(String) BuildingStates
}
class CastleBuilding {
  + CastleBuilding() 
}
class Civilian {
  + Civilian() 
  - boolean hasJob
   boolean hasJob
}
class Colors {
<<enumeration>>
  + Colors() 
  + values() Colors[]
  + valueOf(String) Colors
}
class DefenseRating {
<<enumeration>>
  - DefenseRating(int) 
  - int rate
  + values() DefenseRating[]
  + valueOf(String) DefenseRating
   int rate
}
class EuropeanTroop {
  + EuropeanTroop() 
}
class FileController {
  + FileController() 
  + saveCurrentUser() void
  + saveAllUser() void
  + loadCurrentUser() void
  + loadAllUser() void
}
class Foodstuffs {
<<enumeration>>
  - Foodstuffs(String) 
  - String foodstuff
  + values() Foodstuffs[]
  + valueOf(String) Foodstuffs
   String foodstuff
}
class Game {
  + Game() 
  - ArrayList~Government~ governments
  - int round
  - Government currentGovernment
  - Map map
  + changeTurn() void
   Map map
   Government currentGovernment
   ArrayList~Government~ governments
   int round
}
class GameBuildings {
  + GameBuildings() 
  + createBrewery() void
  + createPitchRig() void
  + createBarrack() void
  + createStockPile() void
  + createIronMine() void
  + createTunnelEntrance() void
  + createStable() void
  + createStairs() void
  + createBigStoneGatehouse() void
  + createFoodWarehouse() void
  + createSmallStoneGatehouse() void
  + createSquareTower() void
  + createMill() void
  + createCagedWarDogs() void
  + createSiegeTent() void
  + createLongWall() void
  + createPoleTurner() void
  + createShortWall() void
  + createWall() void
  + createHopGarden() void
  + createRoundTower() void
  + createInn() void
  + createMercenaryPost() void
  + createBlackSmith() void
  + createFletcher() void
  + createLookoutTower() void
  + createArmory() void
  + createHovel() void
  + createChurch() void
  + createHuntingPost() void
  + createTunnelersGuild() void
  + createDrawBridge() void
  + createOilSmelter() void
  + createTurret() void
  + createDairyProducts() void
  + createWheatFarm() void
  + createWoodCutter() void
  + createOxTether() void
  + createAppleGarden() void
  + createPerimeterTower() void
  + createKillingPit() void
  + createArmourer() void
  + createEngineerGuild() void
  + createQuarry() void
  + createShop() void
  + createCathedral() void
  + createBakery() void
}
class GameController {
  + GameController() 
  + attackEnemy(int, int) String
  + disbandUnit() String
  + moveUnit(int, int) String
  + pourOil(String) String
  + buildEquipment() String
  + createUnit(String, int) String
  + patrolUnit(int, int, int, int) String
  + dropBuilding(int, int, String) String
  + digTunnel(int, int) String
  + selectBuilding(int, int) String
  + selectUnit(int, int) String
  + airAttack(int, int) String
  + repairCastleBuildings() String
  + setStateOfMilitary(int, int, String) String
  + changeTurn() String
}
class GameFoods {
  + GameFoods() 
  # createApple() void
  # createFlour() void
  # createWheat() void
  # createHops() void
  # createAle() void
  # createCheese() void
  # createBread() void
  # createMeat() void
  # addFoods() void
}
class GameGoods {
  + GameGoods() 
  + addGoods() void
}
class GameHumans {
  + GameHumans() 
  + createSlinger() void
  + createSlave() void
  + createEngineer() void
  + createTunneler() void
  + createBlackMonk() void
  + createSwordsman() void
  + createArcherBow() void
  + createAssassin() void
  + createMaceman() void
  + addArabianMercenaries() void
  + createKnight() void
  + createLadderman() void
  + createHorseArcher() void
  + createFireThrower() void
  + createCrossbowman() void
  + createPikeman() void
  + addLord() void
  + createArcher() void
  + addEuropeanTroops() void
  + addHumans() void
  + createArabianSwordsman() void
  + createSpearman() void
}
class GameMenu {
  + GameMenu() 
  + run(Scanner, Game) void
}
class GameResources {
  + GameResources() 
  # createPitch() void
  # addResources() void
  # createStone() void
  # createWood() void
  # createIron() void
}
class GameWeapons {
  + GameWeapons() 
  # addWeapons() void
  # createBow() void
  # createMace() void
  # createPike() void
  # createSwords() void
  # createSpear() void
}
class Gatehouse {
  + Gatehouse() 
  - int capacity
   int capacity
}
class Goods {
  + Goods() 
  - String name
  - ArrayList~String~ required
   String name
   ArrayList~String~ required
}
class Government {
  + Government() 
  - ArrayList~Building~ buildings
  - int taxRate
  - ArrayList~Human~ society
  - int foodRate
  - int castleY
  - int gold
  - int fearRate
  - HashMap~RawMaterials, Integer~ resources
  - int population
  - int religionRate
  - int weaponCapacity
  - Colors color
  - HashMap~Foodstuffs, Integer~ foods
  - int maxPopulation
  - ArrayList~EuropeanTroop~ europeanTroops
  - HashMap~Weapons, Integer~ weapons
  - ArrayList~ArabianMercenary~ arabianMercenaries
  - int castleX
  + changePopulation() void
   ArrayList~ArabianMercenary~ arabianMercenaries
   int maxPopulation
   HashMap~Weapons, Integer~ weapons
   int religionRate
   int foodRate
   ArrayList~Human~ society
   Colors color
   ArrayList~EuropeanTroop~ europeanTroops
   int castleX
   int castleY
   HashMap~Foodstuffs, Integer~ foods
   int fearRate
   int taxRate
   int gold
   int population
   int weaponCapacity
   ArrayList~Building~ buildings
   HashMap~RawMaterials, Integer~ resources
}
class GovernmentController {
  + GovernmentController() 
  + showFoodRate() String
  + showTaxRate() String
  + showPopularityFactors() String
  + changeFoodRate(int) String
  + changeTaxRate(int) String
  + showPopularity() String
  + showFearRate() String
  + showFoodList() String
  + changeFearRate(int) String
}
class Human {
  + Human() 
  - int speed
  - int x
  - int y
  - DefenseRating defenseRating
   int y
   int x
   int speed
   DefenseRating defenseRating
}
class HumanStates {
<<enumeration>>
  + HumanStates() 
  + valueOf(String) HumanStates
  + values() HumanStates[]
}
class LandPermissions {
<<enumeration>>
  + LandPermissions() 
  + values() LandPermissions[]
  + valueOf(String) LandPermissions
}
class LoginMenu {
  + LoginMenu() 
  + run(Scanner) void
}
class Lord {
  + Lord() 
}
class MainController {
  + MainController() 
  + loadGame() void
  + run() void
}
class MainMenu {
  + MainMenu() 
  + run(Scanner) void
}
class Map {
  + Map() 
  - int width
  - Tile[][] mapTiles
  - int length
   int length
   Tile[][] mapTiles
   int width
}
class MapController {
  + MapController() 
  + dropTree(int, int, String) String
  + showDetailsOfLand(int, int) String
  + clearLand(int, int) String
  + dropUnit(int, int, String, int) String
  + showMap(int, int) String
  + dropRock(int, int, String) String
  + moveMap(int, int, int, int) String
  + dropCastleBuildings(int, int, String) String
  + setTexture(int, int, String) String
}
class MapMenu {
  + MapMenu() 
  + run(Scanner) void
}
class Military {
  + Military() 
  - AttackRating attackRating
  - StateOfMilitary militaryState
   StateOfMilitary militaryState
   AttackRating attackRating
}
class MilitaryProducer {
  + MilitaryProducer() 
  - ArrayList~String~ militaryTypes
   ArrayList~String~ militaryTypes
}
class Permission {
  + Permission(String, boolean) 
  - String name
  - boolean state
   String name
   boolean state
}
class PopularityIncreasingBuilding {
  + PopularityIncreasingBuilding() 
  - int increaseRate
   int increaseRate
}
class PrimaryProducer {
  + PrimaryProducer() 
  - String primaryType
   String primaryType
}
class ProducerBuilding {
  + ProducerBuilding() 
}
class ProductProducer {
  + ProductProducer() 
  - String productType
  - ArrayList~String~ primaryRequired
  - ArrayList~String~ buildingRequired
   ArrayList~String~ buildingRequired
   ArrayList~String~ primaryRequired
   String productType
}
class ProfileMenu {
  + ProfileMenu() 
  + run(Scanner) void
}
class RawMaterials {
<<enumeration>>
  + RawMaterials() 
  + values() RawMaterials[]
  + valueOf(String) RawMaterials
}
class SecurityQuestions {
<<enumeration>>
  - SecurityQuestions(String) 
  - String question
  + values() SecurityQuestions[]
  + valueOf(String) SecurityQuestions
   String question
}
class ShopController {
  + ShopController() 
  + sellItem(String, int) String
  + buyItem(String, int) String
  + showPriceList() String
}
class SignupMenu {
  + SignupMenu() 
  + run(Scanner) void
}
class Slogans {
<<enumeration>>
  + Slogans() 
  + values() Slogans[]
  + valueOf(String) Slogans
}
class Speed {
<<enumeration>>
  - Speed(int) 
  - int rate
  + valueOf(String) Speed
  + values() Speed[]
   int rate
}
class StateOfMilitary {
<<enumeration>>
  + StateOfMilitary() 
  + valueOf(String) StateOfMilitary
  + values() StateOfMilitary[]
}
class StorageBuilding {
  + StorageBuilding() 
  - int capacity
  - String itemType
   int capacity
   String itemType
}
class Textures {
<<enumeration>>
  - Textures(String) 
  + valueOf(String) Textures
  + values() Textures[]
}
class Tile {
  + Tile() 
  - Textures texture
  - Building building
   Building building
   Textures texture
   ArrayList~Human~ human
}
class ToolProducer {
  + ToolProducer() 
  - ArrayList~AttackingAndDefendingTool~ tool
   ArrayList~AttackingAndDefendingTool~ tool
}
class Tower {
  + Tower() 
  - int defendRange
  - int fireRange
   int defendRange
   int fireRange
}
class TradeController {
  + TradeController() 
  + tradeGoods(String, int, int, String) String
  + showTradeList() String
  + showTradeHistory() String
  + acceptTrade(int) String
}
class Trees {
<<enumeration>>
  - Trees(String) 
  + values() Trees[]
  + valueOf(String) Trees
}
class User {
  + User(String, String, String, String, String) 
  - String email
  - String username
  - String passwordRecoveryQuestion
  - String passwordRecoveryAnswer
  - String slogan
  - String nickname
  - String password
   String passwordRecoveryAnswer
   String password
   String passwordRecoveryQuestion
   String email
   User userByUsername
   String username
   String nickname
   String slogan
}
class UserController {
  + UserController() 
  + displayRank() String
  + changeUsername(String) String
  + changeSlogan(String) String
  + checkSecurityQuestion(String, String) boolean
  + changeNickname(String) String
  + changeEmail(String) String
  + createUser(String, String, String, String, String) String
  + makeRandomSlogan() String
  + loginUser(String, String, boolean) String
  + validateSignup() String
  - convertPasswordToHash() String
  + pickSecurityQuestion(int, String, String) String
  + displayHighScore() String
  + displaySlogan() String
  + changePassword(String, String) String
  + displayProfile() String
  + logout() String
  + forgotPassword(String) String
  + makeRandomPassword() String
  + removeSlogan() String
}
class Wall {
  + Wall() 
}
class Weapon {
  + Weapon() 
  - ArrayList~Permission~ permissions
   ArrayList~Permission~ permissions
}
class Weapons {
<<enumeration>>
  + Weapons() 
  + values() Weapons[]
  + valueOf(String) Weapons
}
class killingPit {
  + killingPit() 
  - int damage
   int damage
}

Application "1" *--> "users *" User 
ArabianMercenary  -->  Military 
AttackingAndDefendingTool "1" *--> "government 1" Government 
AttackingAndDefendingTool "1" *--> "engineers *" Human 
AttackingAndDefendingTool "1" *--> "permissions *" Permission 
AttackingAndDefendingTool "1" *--> "speed 1" Speed 
Building "1" *--> "state 1" BuildingStates 
Building "1" *--> "government 1" Government 
Building "1" *--> "requiredHumans *" Human 
Building "1" *--> "landPermissions *" Permission 
CastleBuilding  -->  Building 
Civilian  -->  Human 
EuropeanTroop  -->  Military 
Game "1" *--> "governments *" Government 
Game "1" *--> "map 1" Map 
GameBuildings "1" *--> "buildings *" Building 
GameController "1" *--> "game 1" Game 
GameFoods  -->  GameGoods 
GameGoods "1" *--> "goods *" Goods 
GameHumans "1" *--> "humans *" Human 
GameResources  -->  GameGoods 
GameWeapons  -->  GameGoods 
Gatehouse  -->  CastleBuilding 
Government "1" *--> "arabianMercenaries *" ArabianMercenary 
Government "1" *--> "buildings *" Building 
Government "1" *--> "color 1" Colors 
Government "1" *--> "europeanTroops *" EuropeanTroop 
Government "1" *--> "foods *" Foodstuffs 
Government "1" *--> "society *" Human 
Government "1" *--> "resources *" RawMaterials 
Government "1" *--> "user 1" User 
Government "1" *--> "weapons *" Weapons 
GovernmentController "1" *--> "currentGovernment 1" Government 
Human "1" *--> "defenseRating 1" DefenseRating 
Human "1" *--> "government 1" Government 
Human "1" *--> "state 1" HumanStates 
Lord  -->  Military 
MainController "1" *--> "currentUser 1" User 
Map "1" *--> "mapTiles *" Tile 
Military "1" *--> "attackRating 1" AttackRating 
Military  -->  Human 
Military "1" *--> "militaryState 1" StateOfMilitary 
MilitaryProducer  -->  ProducerBuilding 
PopularityIncreasingBuilding  -->  Building 
PrimaryProducer  -->  ProducerBuilding 
ProducerBuilding  -->  Building 
ProductProducer  -->  ProducerBuilding 
StorageBuilding  -->  Building 
Tile "1" *--> "building 1" Building 
Tile "1" *--> "humans *" Human 
Tile "1" *--> "texture 1" Textures 
ToolProducer "1" *--> "tool *" AttackingAndDefendingTool 
ToolProducer  -->  ProducerBuilding 
Tower  -->  CastleBuilding 
Wall  -->  CastleBuilding 
Weapon  -->  Goods 
Weapon "1" *--> "permissions *" Permission 
killingPit  -->  Building 
