# Company App

Aplikacja która integruje się z PostgreSQL i jest uruchamiana w kontenerze Docker.

## Stack

- Java 21
- Spring Web MVC
- Spring Data JPA
- Maven
- Docker
- PostgreSQL

## Testowanie za pomocą np. Postman

GET
http://localhost:8080/api/v1/company/{id} np:
http://localhost:8080/api/v1/company/1

POST
http://localhost:8080/api/v1/company
BODY:
{
    "name" : "Example Company Name 1",
    "departments" : [
        {
            "name" : "Example Department Name 1",
            "teams" : [
                {
                    "name" : "Example Team Name 1",
                    "projects" : [
                    {
                            "manager" : {
                                "contactInformation" : "Example contactInformation 1"
                            }
                        }
                    ]
                }
            ]
        }
    ]
}

PUT
http://localhost:8080/api/v1/company
BODY:
{
    "id": 1,
    "name": "Change Example Company Name 5",
    "departments": [
        {
            "id": 1,
            "name": "Change Example Department Name 5",
            "teams": [
                {
                    "id": 1,
                    "name": "Example Team Name 5",
                    "projects": [
                        {
                            "id": 1,
                            "manager": {
                                "id": 1,
                                "contactInformation": "Change Example contactInformation 5"
                            }
                        }
                    ]
                }
            ]
        }
    ]
}

DELETE
http://localhost:8080/api/v1/company/{id} np:
http://localhost:8080/api/v1/company/1
