classDiagram
direction BT
class ActivityPermissions {
<<enumeration>>
  + ActivityPermissions() 
  + values() ActivityPermissions[]
  + valueOf(String) ActivityPermissions
}
class Application {
  + Application() 
  - ArrayList~User~ users
  - User currentUser
  + isUserExists(String) boolean
  + getUserByUsername(String) User?
   ArrayList~User~ users
   User currentUser
}
class ArabianMercenary {
  + ArabianMercenary(Government, int, int, int, DefenseRating, int, int, AttackRating) 
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
  + AttackingAndDefendingTool(Government, int, int, int, Speed, int) 
  - Government government
  - int x
  - int shootingRange
  - ArrayList~Human~ engineers
  - Speed speed
  - int numberOfRequiredEngineers
  - ArrayList~Permission~ permissions
  - int y
   int y
   int numberOfRequiredEngineers
   int x
   int shootingRange
   ArrayList~Permission~ permissions
   Government government
   Speed speed
   ArrayList~Human~ engineers
}
class Building {
  + Building(Government, int, int, String, int, int, int, int, int) 
  - int hp
  - ArrayList~Permission~ activityPermissions
  - int startX
  - ArrayList~Human~ requiredHumans
  - String type
  - int endY
  - int numberOfRequiredEngineers
  - int startY
  - ArrayList~Permission~ landPermissions
  - int numberOfRequiredWorkers
  - HashMap~String, Integer~ cost
  - int endX
   ArrayList~Permission~ landPermissions
   HashMap~String, Integer~ cost
   String type
   int numberOfRequiredEngineers
   int startY
   int startX
   int endY
   int endX
   ArrayList~Permission~ activityPermissions
   ArrayList~Human~ requiredHumans
   int hp
   int numberOfRequiredWorkers
}
class BuildingMenu {
  + BuildingMenu() 
  + run(Scanner) void
}
class BuildingStates {
<<enumeration>>
  + BuildingStates() 
  + valueOf(String) BuildingStates
  + values() BuildingStates[]
}
class CastleBuilding {
  + CastleBuilding(Government, int, int, String, int, int, int, int, int) 
}
class Civilian {
  + Civilian(Government, int, int, int, DefenseRating, int, int) 
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
  + valueOf(String) DefenseRating
  + values() DefenseRating[]
   int rate
}
class EuropeanTroop {
  + EuropeanTroop(Government, int, int, int, DefenseRating, int, int, AttackRating) 
}
class FileController {
  + FileController() 
  + loadCurrentUser() void
  + saveCurrentUser() void
  + loadAllUser() void
  + saveAllUser() void
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
  + Game(Map) 
  - int round
  - ArrayList~Government~ governments
  - Government currentGovernment
  - Map map
  + changeTurn() void
   Map map
   int round
   Government currentGovernment
   ArrayList~Government~ governments
}
class GameBuildings {
  + GameBuildings() 
  + createHovel() void
  + createBarrack() void
  + createCagedWarDogs() void
  + createWoodCutter() void
  + createBlackSmith() void
  + createPerimeterTower() void
  + createIronMine() void
  + createRoundTower() void
  + createTunnelersGuild() void
  + createOilSmelter() void
  + createOxTether() void
  + createHuntingPost() void
  + createTunnelEntrance() void
  + createChurch() void
  + createSiegeTent() void
  + createStable() void
  + createMill() void
  + createQuarry() void
  + createWall() void
  + createPitchRig() void
  + createCathedral() void
  + createPoleTurner() void
  + createAppleGarden() void
  + createWheatFarm() void
  + createBigStoneGatehouse() void
  + createInn() void
  + createLookoutTower() void
  + createFoodWarehouse() void
  + createSquareTower() void
  + createHopGarden() void
  + createLongWall() void
  + createArmory() void
  + createArmourer() void
  + createTurret() void
  + createKillingPit() void
  + createDairyProducts() void
  + createBrewery() void
  + createShop() void
  + createMercenaryPost() void
  + createFletcher() void
  + createEngineerGuild() void
  + createShortWall() void
  + createDrawBridge() void
  + createStockPile() void
  + createStairs() void
  + createSmallStoneGatehouse() void
  + createBakery() void
}
class GameController {
  + GameController() 
  + buildEquipment() String
  + setStateOfMilitary(int, int, String) String
  + airAttack(int, int) String
  + selectUnit(int, int) String
  + repairCastleBuildings() String
  + patrolUnit(int, int, int, int) String
  + changeTurn() String
  + moveUnit(int, int) String
  + disbandUnit() String
  + selectBuilding(int, int) String
  + pourOil(String) String
  + createUnit(String, int) String
  + attackEnemy(int, int) String
  + digTunnel(int, int) String
  + dropBuilding(int, int, String) String
}
class GameFoods {
  + GameFoods() 
  # createBread() void
  # createCheese() void
  # createApple() void
  # createAle() void
  # createFlour() void
  # createMeat() void
  # createHops() void
  # addFoods() void
  # createWheat() void
}
class GameGoods {
  + GameGoods() 
  + addGoods() void
}
class GameHumans {
  + GameHumans() 
  + createAssassin() void
  + createSpearman() void
  + createArabianSwordsman() void
  + addArabianMercenaries() void
  + createPikeman() void
  + createCrossbowman() void
  + createArcher() void
  + addHumans() void
  + createArcherBow() void
  + createFireThrower() void
  + createEngineer() void
  + addLord() void
  + createKnight() void
  + createSlave() void
  + addEuropeanTroops() void
  + createTunneler() void
  + createLadderman() void
  + createMaceman() void
  + createSwordsman() void
  + createHorseArcher() void
  + createBlackMonk() void
  + createSlinger() void
}
class GameMenu {
  + GameMenu() 
  + run(Scanner, Game) void
}
class GameResources {
  + GameResources() 
  # createPitch() void
  # createWood() void
  # addResources() void
  # createStone() void
  # createIron() void
}
class GameWeapons {
  + GameWeapons() 
  # createSpear() void
  # createMace() void
  # addWeapons() void
  # createPike() void
  # createSwords() void
  # createBow() void
}
class Gatehouse {
  + Gatehouse(Government, int, int, String, int, int, int, int, int, int) 
  - int capacity
   int capacity
}
class Goods {
  + Goods(String) 
  - String name
  - ArrayList~String~ required
   String name
   ArrayList~String~ required
}
class Government {
  + Government(User, int, int, Colors) 
  - int taxRate
  - int castleY
  - Colors color
  - int population
  - int maxPopulation
  - ArrayList~Building~ buildings
  - ArrayList~ArabianMercenary~ arabianMercenaries
  - int castleX
  - ArrayList~Human~ society
  - int gold
  - int weaponCapacity
  - int fearRate
  - int foodRate
  - int religionRate
  - ArrayList~EuropeanTroop~ europeanTroops
  - HashMap~Weapons, Integer~ weapons
  - HashMap~Foodstuffs, Integer~ foods
  - HashMap~RawMaterials, Integer~ resources
  + changePopulation() void
   int religionRate
   ArrayList~ArabianMercenary~ arabianMercenaries
   HashMap~Weapons, Integer~ weapons
   int foodRate
   int maxPopulation
   ArrayList~Human~ society
   Colors color
   ArrayList~EuropeanTroop~ europeanTroops
   int taxRate
   int population
   HashMap~Foodstuffs, Integer~ foods
   int fearRate
   int castleY
   int castleX
   ArrayList~Building~ buildings
   int gold
   int weaponCapacity
   HashMap~RawMaterials, Integer~ resources
}
class GovernmentController {
  + GovernmentController() 
  + showPopularityFactors() String
  + showPopularity() String
  + showTaxRate() String
  + changeFoodRate(int) String
  + changeTaxRate(int) String
  + showFearRate() String
  + showFoodList() String
  + showFoodRate() String
  + changeFearRate(int) String
}
class Human {
  + Human(Government, int, int, int, DefenseRating, int, int) 
  - int y
  - int x
  - DefenseRating defenseRating
  - int speed
   int y
   int x
   int speed
   DefenseRating defenseRating
}
class HumanStates {
<<enumeration>>
  + HumanStates() 
  + values() HumanStates[]
  + valueOf(String) HumanStates
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
  + Lord(Government, int, int, int, DefenseRating, int, int, AttackRating) 
}
class MainController {
  + MainController() 
  + run() void
  + loadGame() void
}
class MainMenu {
  + MainMenu() 
  + run(Scanner) void
}
class Map {
  + Map(int, int, Tile[][]) 
  - Tile[][] mapTiles
  - int length
  - int width
   int length
   int width
   Tile[][] mapTiles
}
class MapController {
  + MapController() 
  + dropRock(int, int, String) String
  + setTexture(int, int, String) String
  + dropUnit(int, int, String, int) String
  + clearLand(int, int) String
  + dropTree(int, int, String) String
  + dropCastleBuildings(int, int, String) String
  + showMap(int, int) String
  + showDetailsOfLand(int, int) String
  + moveMap(int, int, int, int) String
}
class MapMenu {
  + MapMenu() 
  + run(Scanner) void
}
class Military {
  + Military(Government, int, int, int, DefenseRating, int, int, AttackRating) 
  - AttackRating attackRating
  - StateOfMilitary militaryState
   StateOfMilitary militaryState
   AttackRating attackRating
}
class MilitaryProducer {
  + MilitaryProducer(Government, int, int, String, int, int, int, int, int) 
  - ArrayList~String~ militaryTypes
   ArrayList~String~ militaryTypes
}
class Permission {
  + Permission(String, boolean) 
  - boolean state
  - String name
   String name
   boolean state
}
class PopularityIncreasingBuilding {
  + PopularityIncreasingBuilding(Government, int, int, String, int, int, int, int, int, int) 
  - int increaseRate
   int increaseRate
}
class PrimaryProducer {
  + PrimaryProducer(Government, int, int, String, int, int, int, int, int, String) 
  - String primaryType
   String primaryType
}
class ProducerBuilding {
  + ProducerBuilding(Government, int, int, String, int, int, int, int, int) 
}
class ProductProducer {
  + ProductProducer(Government, int, int, String, int, int, int, int, int, String) 
  - String productType
  - ArrayList~String~ buildingRequired
  - ArrayList~String~ primaryRequired
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
  + showPriceList() String
  + buyItem(String, int) String
  + sellItem(String, int) String
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
  + values() Speed[]
  + valueOf(String) Speed
   int rate
}
class StateOfMilitary {
<<enumeration>>
  + StateOfMilitary() 
  + valueOf(String) StateOfMilitary
  + values() StateOfMilitary[]
}
class StorageBuilding {
  + StorageBuilding(Government, int, int, String, int, int, int, int, int, String, int) 
  - int capacity
  - String itemType
   String itemType
   int capacity
}
class Textures {
<<enumeration>>
  - Textures(String) 
  + valueOf(String) Textures
  + values() Textures[]
}
class Tile {
  + Tile(Textures) 
  - Textures texture
  - Building building
   Building building
   Textures texture
   ArrayList~Human~ human
}
class ToolProducer {
  + ToolProducer(Government, int, int, String, int, int, int, int, int) 
  - ArrayList~AttackingAndDefendingTool~ tool
   ArrayList~AttackingAndDefendingTool~ tool
}
class Tower {
  + Tower(Government, int, int, String, int, int, int, int, int, int, int) 
  - int fireRange
  - int defendRange
   int fireRange
   int defendRange
}
class TradeController {
  + TradeController() 
  + showTradeHistory() String
  + acceptTrade(int) String
  + showTradeList() String
  + tradeGoods(String, int, int, String) String
}
class Trees {
<<enumeration>>
  - Trees(String) 
  + valueOf(String) Trees
  + values() Trees[]
}
class User {
  + User(String, String, String, String, String) 
  - String slogan
  - String username
  - String nickname
  - String passwordRecoveryQuestion
  - String passwordRecoveryAnswer
  - String password
  - String email
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
  + pickSecurityQuestion(int, String, String) String
  + loginUser(String, String, boolean) String
  + changeEmail(String) String
  + checkSecurityQuestion(String, String) boolean
  + removeSlogan() String
  + displayProfile() String
  + logout() String
  + displayRank() String
  + forgotPassword(String) String
  + displayHighScore() String
  - convertPasswordToHash() String
  + makeRandomSlogan() String
  + changeNickname(String) String
  + validateSignup() String
  + displaySlogan() String
  + changePassword(String, String) String
  + changeUsername(String) String
  + createUser(String, String, String, String, String) String
  + makeRandomPassword() String
  + changeSlogan(String) String
}
class Wall {
  + Wall(Government, int, int, String, int, int, int, int, int, int) 
}
class Weapon {
  + Weapon(String) 
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
  + killingPit(Government, int, int, String, int, int, int, int, int, int) 
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
