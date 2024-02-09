Ce projet JAVA est une opportunité pour nous de pouvoir apprendre un nouveau langage de programmation et son utilité. Le fait d’apprendre ce langage à travers un jeu rend la tâche encore plus instructive.
Grâce à ce jeu nous avons découvert le langage de programmation Java et nous avons appris à quoi servent les différentes classe et comment les utiliser.

Dans notre jeu, nous avons ajouté les fonctionnalités suivantes :
-	Ecran Titre : Nous allons entrer dans le jeu en cliquant sur un bouton (peu importe lequel)
-	Game Over : Lorsque le personnage meurt, un écran indique « Game Over »
-	Indicateur de vie : Une barre de vie concernant le héros diminuera lorsqu’il y a une collision avec un ennemi ou un piège
-	Présence de piège : Des pièges seront présent dans le jeu et diminuerons la vie du héros

Dans la répartition des tâches nous avons procédés dans un premier temps par la mise en place de l’indicateur de vie puis la présence de piège, et dans un second temps par le Game Over puis l’écran titre.
Lorsque nous avons écrit le code pour les différentes fonctionnalité à ajouter, la partie qui nous a le plus intéressé a été l’indicateur de vie.
Concernant la barre de vie du héros, nous avons ajouté dans le MainInterface une classe pour la barre de vie nommée class HealthBar.
Cette classe défini la dimension de la barre de vie via la fonction setPreferredSize(new Dimension()) ainsi que le pourcentage de vie via la fonction healthPercentage (qu’on a défini à 100% dans notre cas). Le pourcentage de vie est une fonctionnalité que nous avons ajouté à notre barre de vie afin de ne pas avoir une barre de vie classique qui baisse sans voir le pourcentage de vie restant.

Ensuite, nous avons la fonction ‘’setHealthPercentage(double percentage)’’ dans la classe ‘‘HealthBar’’ qui permet de mettre à jour le pourcentage de santé dans la barre de vie.
Lorsque cette fonction est appelée avec un nouveau pourcentage (diminution de la santé du héros), elle met à jour la variable ‘‘healthPercentage’’ et demande à Swing de redessiner la barre de santé en appelant la méthdde ‘’repaint()’’. Cette méthode nous permet donc d’actualiser la barre de vie en fonction des dommages accumulé par le héros.

Cette étape nous a permis d’en apprendre plus sur l’utilisation des différentes classes mais c’est aussi la partie qui a été la plus compliqué (entre les 4 tâches à éffectuer) car il fallait faire en sorte que la barre de vie soit la plus optimale possible avec l’actualisation de la barre de vie ainsi que du pourcentage et une fois à 0% le game over qui s’affiche et la barre de vie qui revient à 100% une fois le jeu relancé.
Ce qui était intéressant aussi était de voir comment la compilation du jeu se faisait et comment à travers l’interface IntelliJ le jeu pouvait s’exécuter.
