mvn -q exec:java -Dexec:mainClass="swingy.App"
java -jar target/swingy-1.0.jar console|gui

Swingy rules:

CHARACTER CREATION:
-Name must contain only letters.
-Name must be between 3 and 20 characters long.
-Must pick from one of the following classes (ATK, DEF, HP): 
    - Big Swordfish Tank (2, 5, 10) on level up (+1, +2, +1)
    - Big Villain Arsenal (5, 2, 10) on level up (+2, +1, +1)
    - Healthy Lt Gabriel Cash Coder (3, 3, 14) on level up (+1, +1, +2)
    - Losing Programmer (1, 1, 1) on level up (+0, +0, +0)

BACKPACK:
-Can only hold one of each artefact.
-Artefacts are obtained randomly from battle.
-Artefacts are:
    - Weapon (ATK boost)
    - Armour (DEF boost)
    - Helm (HP boost)