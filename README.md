# TestFaces - KochsShop

## Installation
### System Requirements
* Linux Ubuntu (tested in VirtualBox VM)
* Package mysql-server (tested)
* Package mysql-client (tested)
* Eclipse IDE (tested)
* Tomcat created by Eclipse (tested)
* git client (tested)

#### Installing the Requirements on Linux VM
```sudo apt-get install openjdk-11-jre -y``` 
```sudo apt-get install openjdk-11-jdk -y```
```sudo apt-get install mysql-client mysql-server -y``` 
```sudo apt-get install git -y```
```sudo apt-get install eclipse -y```

### Adding Tomcat 
#### Inside Eclipse [TESTED]
1.Start Eclipse
2.go to the servers Tab
3.click on add new Server
4.Do your options (chosing the install folder, downloading the Tomcat etc.)
5.Afterwards you can use it inside Eclipse via the Servers Tab (maybe you have to select Java EE Views)

#### As Linux System Service (systemctl or service) [UNTESTED]
1.`sudo apt-get install tomcat9 -y`
2.Now configure it with Eclipse (maybe with help by StackOverflow, DuckDuckGo or YouTube)
3.Same as above (inside Eclipses Server Tab)

### Installing the Code of this project
1. open the Terminal
2. navigate via cd <dir> to the folder where you wish the files (e.g. /home/<user>/eclipse-workspace/)
3. git clone https://github.com/ksaadDE/JavaWebShop/ 
4. start Eclipse and import the Project by clicking in the top Menubar "File" -> "Open Projects from FileSystem"
5. Select the right Server for the Project (greenarrow + select the server + check the "always use this.. for this project" checkbox)
6. Have fun <3


## Usage
After the installation you can go to: http://localhost:8080/TestFaces/faces/index.xhtml
It's important that you use that URL as entry point, due to some misconfiguration. 
Maybe you are lucky and you can fix it!

# License
This Project uses Libraries by Third Parties e.g. GJSON by Google, BCrypt and others. Their work is differently licensed, however their license stuff is inside the Project.
I don't claim ownership of their work, only my work is my own.

My own work is licensed under CC BY-NC 4.0, so free for personal use (and educational!), but no commercial usage && Sharing / adapting allowed for non commercial purposes! Attribution is required!
A quick ref. to the Lic itself: https://creativecommons.org/licenses/by-nc/4.0/
for the long Lic please read the LICENSE file inside this repo

# Quick Links:
## Java Source
https://github.com/ksaadDE/JavaWebShop/tree/master/src

##WebContent
https://github.com/ksaadDE/JavaWebShop/tree/master/WebContent

