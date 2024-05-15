# Návrh a implementace helpdesku pro vybrané zdravotnické zařízení - Backend
## Autor: Vladyslav Babyč

### Návod na spuštení aplikace

Pro spuštění aplikace je nutné mít nainstalovaný software [Docker](https://www.docker.com)

Po stažení repositáře je potřeba nejdříve vytvořit image aplikace a nahrát jí do Docker Hubu:

```bash
docker buildx build --platform linux/amd64,linux/arm64 -t <username/repository:tag-name> --push .
```

Poté je potřeba vytvořit persistentní úložiště a spustit databázi aplikace pomocí příkazu:

Vytvoření persistentního úložiště:
```bash
docker volume create <volume-name>
```
Spuštění databáze:
```bash
docker run --name <container-name> -p 5432:5432 -e POSTGRES_USER=<postgres-user> -e POSTGRES_PASSWORD=<postgres-password> -e POSTGRES_DB=<db-name> -v <volume-name>:/var/lib/postgresql/data -d postgres 
```

Následně můžeme spustit aplikaci pomocí příkazu:

```bash
docker run --rm --name=<container-name> -p 8080:8080 -d <username/repository:tag-name>
```

Pozn.: Proměnné v závorkách <> je potřeba nahradit skutečnými hodnotami a tyto hodnoty nastavit i v souboru application.properties.

Pozn.: Pro nahrání aplikace do Docker Hubu je potřeba mít účet na této platformě.