#Analyse et transformation de code source


####_Objectif_

Analyser et transformer du code Java fail-fast en fail-safe.

###_Contributeurs_
* Dieulin MAMBOUANA (Github : [@Dieulin](https://github.com/Dieulin))
* Romain Sommerard (Github : [@sommerard](https://github.com/rsommerard))

###_Outil_
Nous utilisons [_Spoon_](http://spoon.gforge.inria.fr/) comme moteur de transformation. Un puissant outil mis à disposition par l'INRIA pour analyser et transformer efficacement du code Java.

##_Validation_
Nous avons testé notre processeur de transformation sur le projet [_Apache Commons Email_](http://commons.apache.org/proper/commons-email/).

![alt text](http://commons.apache.org/ "Logo Apache Commons")

###_Mode opératoire_
Pour valider nos tranformations sur _Apache Commons Email_ nous avons :
* Testé le fonctionnement natif de _Apache Commons Email_
* Appliqué nos transformations sur _Apache Commons Email_
* Testé le fonctionnement de la version transformée _Apache Commons Email_ sur quelques exemples.

### Lancer le projet

Nous avons créé un script qui package le processeur de transformation, l'importe dans le projet _Apache Commons Email_ et repackage ce dernier pour l'importer dans le projet d'exemple.

Pour ce faire, il suffit de lancer le script: `./run_all.sh`
