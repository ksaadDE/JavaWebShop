# TestFaces - KochsShop

## Installation
### System Requirements
Linux Ubuntu (tested in VirtualBox VM)
Package mysql-server (tested)
Package mysql-client (tested)
Eclipse IDE (tested)
Tomcat created by Eclipse (tested)
git client (tested)

#### Installing the Requirements on Linux VM
sudo apt-get install openjdk-11-jre -y
sudo apt-get install openjdk-11-jdk -y
sudo apt-get install mysql-client mysql-server -y 
sudo apt-get install git -y
sudo apt-get install eclipse -y

### Adding Tomcat 
I've tested it with Tomcat. You need to go to Servers Tab in Eclipse and then click on add new, adjusting the Folders and you can give Eclipse the task to download the correct server. Eclipse will manage Tomcat then, so you can build, restart etc. inside Eclipse without the need to run it as Linux Service. However currently the Installation as Linux Service is untested, but you can export the Project as .war and install it via the Web Interface of Tomcat (which is pre-installed per default, you just need the credentials #duckduckgo)


### Installing the Code of this project
1. Downloading the Code (so called cloning)
git clone <url of this repository> 
2. "Importing" the Project to Eclipse
After settingup Eclipse and Tomcat click on "File" -> "open project folders" -> select the folder where the content of this project is located (where you did git clone and the sub folder of it)

# Java Source
https://github.com/ksaadDE/JavaWebShop/tree/master/src

# WebContent
https://github.com/ksaadDE/JavaWebShop/tree/master/WebContent

