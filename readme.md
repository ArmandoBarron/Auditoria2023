# Manual de instalación del servicio web para el proyecto de auditoria

Requerimientos del sistema

* Ubuntu
* Apache 2
* Php 8.0 o superior y dependencias
* Composer
* Mpdf
* Mysql 7 o superior
* Mysql Workbench (opcional)

## Instalar Apache 2 

Los comandos utilizados para realizar la instalación y configuración básica del servidor apache

```
sudo apt-get install apache2
sudo apt-get install ufw
sudo ufw allow http
sudo ufw allow https
sudo systemctl status apache2
```

## Instalar php 8

```
sudo apt install ca-certificates apt-transport-https software-properties-common
sudo apt-get update
sudo add-apt-repository ppa:ondrej/php
sudo apt install php8.0 libapache2-mod-php8.0
sudo apt install php8.0-fpm libapache2-mod-fcgid
sudo apt install php8.0-mbstring
sudo apt install php8.0-gd
sudo apt install php8.0-bcmath
sudo apt install php8.0-common php-bcmath 
sudo apt install php8.0-common php-fpdi
sudo apt install php8.0-common php-fpdf
sudo apt install php8.0-common php-log 
sudo apt install php8.0-common php-random-compat 
sudo apt install php8.0-common php-deepcopy 
sudo apt install php8.0-mysqli
sudo apt install php8.0-curl
sudo service apache2 restart
```

## Instalar mysql

```
sudo apt-get install mysql-server
sudo systemctl status mysql
```

## Instalar composer

Puedes seguir la instalación de la aplicación Composer en el siguiente url: 

https://phoenixnap.com/kb/how-to-install-composer-ubuntu-16-04

```
sudo apt-get install curl php-cli php-mbstring git unzip
curl -sS https://getcomposer.org/installer -o composer-setup.php
sudo php composer-setup.php --install-dir=/usr/local/bin --filename=composer
composer

now into the folder with the web app run:
composer require mpdf/mpdf

```

