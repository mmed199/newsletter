Fonctionalités : 
Notre application permet un filtrage multiple sur l'Api newsApi
On peut filtrer par pays, categorie, editeur ou en utilisant plusieurs filtres
Le filtre editeur affiche par defaut la liste de tout les editeurs
Si on choisit un pays, la liste des edituer s'actualise avec les editeurs du pays selectionné
L'application interoge le EndPoint HeadLines pour reecupere les articles d'un pays vu que le EndPoint EveryThing ne donne pas cette option 
On peut ajouter ou supprimer des article en favoris pour les sauvegarder en local 

Librairies :
retrofit, okhttp, gson pour consommer l'API 
Room pour la base de donnes local
Skeleton et shimmerlayout pour afficher un Skeleton en attendant le chargement des articles
