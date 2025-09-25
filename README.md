création de l'interface de jeu.

Etape1: 
création d'une JFrame avec une barre menu, un titre dans JLabel et un Jpanel pour l'espece de jeu
Etape 2:
tracer la grille et à chaque fois qu'on clique, on met à jour le graphic en traçant un point
classe point qui contient les coordonnées de celui-ci et une méthde pour le tracer ( pour l'instant sous la forme d'un cercle uniquement de taille fixe)
classe Dessin, hérite Jpanel, permet de dessiner l'interface de jeu, c'est-à-dire la grille et les pions. 
  Attributs = liste des pions présents sur la grille à tracer,
  Méthodes: addPion et paint pour dessiner l'interface
