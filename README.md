#Analyse et transformation de code source


####_Objectif_
Analyser et transformer de manière automatique le code source d'un projet réel open source de fail-fast à fail-safe.

###_Contributeurs_
* Dieulin MAMBOUANA ([@Dieulin](https://github.com/Dieulin))
* Romain Sommerard([@sommerard](https://github.com/rsommerard))

##Projet choisi pour le test
Nous avons prévu de tester nos transformations sur _Jetty project_, un moteur Java open source basé sur le Server Web et mis à disposition par la fondation Eclipse. 

![alt text](https://cloud.githubusercontent.com/assets/14126801/10949926/b01ca386-8335-11e5-869a-86fe48b9bb7a.png "Logo Jetty")

Pour nos expériementations nous avons choisi la version 8 stable dont le code source est librement téléchargeable depuis les liens officiels suivants :
* http://git.eclipse.org/c/jetty/org.eclipse.jetty.project.git/tag/?id=jetty-8.1.8.v20121106
* http://git.eclipse.org/c/jetty/org.eclipse.jetty.project.git/

> Nous avons également limité notre périmètre d'intervention sur l'ensemble du contenu du repertoire _jetty-xml_.

####_Mode opératoire_
Pour valider nos tranformations sur _Jetty_ nous avons:
* Tester le fonctionnement natif de _Jetty_
* Appliquer la transformation sur _Jetty_
* Tester le fonctionnement de la version transformée
* Vérifier que cette dernière conserve le même mode de fonctionnement que la version originale

