#!/bin/bash

## Calculo de Hashes pra proyecto SIVEI Mexico 2023
## Version 2023032901

## Variables
myenvironment="audit1"
mydomain="mx.tivi.global"
user="abarron"


echo "Integrity Verification" 
echo "Timestamp $(date)"

for H in alpha bravo charlie console; 
do 
   echo
   echo "*** Host: $H.$myenvironment.$mydomain ***"
   ssh -T $user@$H.$myenvironment.$mydomain sudo bash << "EOF"
     echo "Binarios Tivi" 
     find /usr/bin -type f -name tivi\* -exec sha256sum '{}' \;
     find /usr/bin -type f -name honeybee\* -exec sha256sum '{}' \;
     echo 
     echo "Configuraciones Tivi"
     find /etc/tivi* -type f -exec sha256sum '{}' \;
     echo 
     echo "Librerias Tivi"
     find /var/lib/tivi* -not \( -path "*choices" -prune -type d \) -a -not \( -path "*log" -prune -type d \) -type f -exec sha256sum '{}' \;

     echo 
     if [ -x "$(command -v docker)" ]; then
        echo "TIVI images"
        docker images --format 'ID({{.ID}})\tSIZE({{.Size}})\tIMG({{.Repository}}:{{.Tag}})' --no-trunc
        echo
        echo "TIVI mx containers"
        docker ps --format 'ID({{.ID}})\tIMAGE({{.Image}})\tSTATE({{.State}})\tNAME({{.Names}})' --no-trunc | grep "\-mx\-"
        echo

        # Create a list of containers
        docker ps --format '{{.Names}}' | grep "\-mx\-" > /tmp/list.tmp
        
        # Calculate the hash of the honeybee binaries on each one
        
        cat /tmp/list.tmp | xargs -I '{}' docker exec {} bash -c "echo;echo Container: {};sha256sum /usr/bin/honeybee*;sha256sum /shared/*.*"
        rm /tmp/list.tmp
     else
       echo "No docker installed"
     fi

     ### Apache
     echo
     echo "Apache binary file"
     sha256sum /usr/sbin/apache2
     echo 
     echo "Apache config files"
     find /etc/apache2/ -type f -exec sha256sum '{}' \;
     echo 
     echo "Apache files served"
     find /var/www/html/ -type f -exec sha256sum '{}' \;
  
     ### HAProxy
     echo
     echo "HAProxy binary file"
     sha256sum /usr/sbin/haproxy
     echo 
     echo "HAProxy config files"
     find /etc/haproxy/ -type f -exec sha256sum '{}' \;
EOF

    if [ "$H" == "console" ]; then
     ssh -T $user@$H.$myenvironment.$mydomain sudo bash << "EOF"
       echo
       echo "Keycloak Database H2 in console"
       find /opt/keycloak* -name keycloakdb.mv.db -type f -exec sha256sum '{}' \;        
       echo 
       echo "TIVI-Console events"
       find /var/lib/tivi-console/elections/ -type f \( -name \*database.db -o -name \*choices.tar \) -type f -exec sha256sum '{}' \; 
EOF
    fi

    ### Aws cli to obtain data from ECR 
    echo
    echo "ECR container data"
    aws ecr describe-images --repository-name civik-keycloak-mx-simulacrove2023
done
