# 🏢 Smart Building — Surveillance en temps réel via MQTT

> Projet de démonstration d'une architecture IoT pour bâtiments intelligents : simulation de capteurs, broker MQTT, pont WebSocket et dashboard web temps réel.

---

## 📋 Table des matières

- [Présentation](#-présentation)
- [Fonctionnalités](#-fonctionnalités)
- [Architecture](#-architecture)
- [Technologies utilisées](#-technologies-utilisées)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Lancement](#-lancement)
- [Utilisation](#-utilisation)
- [Captures d'écran](#-captures-décran)

---

## 📖 Présentation

**Smart Building** est un projet Java illustrant une architecture IoT complète :

1. Des **capteurs simulés** publient des données (température, humidité, détection de fumée) sur un broker MQTT.
2. Un **serveur backend** s'abonne aux topics MQTT et transmet les données en temps réel via WebSocket.
3. Un **dashboard web** reçoit et affiche les données en direct dans le navigateur.

---

## ✨ Fonctionnalités

- 🌡️ Simulation de capteurs IoT (température, humidité, fumée) en temps réel
- 📡 Publication de données via le protocole MQTT (Eclipse Paho)
- 🔄 Pont MQTT → WebSocket pour diffusion vers le navigateur
- 🖥️ Dashboard web temps réel sans rechargement de page
- 🏗️ Architecture modulaire et extensible (multi-salles possible)

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Smart Building MQTT                       │
│                                                              │
│  ┌──────────────────┐        ┌──────────────────────────┐   │
│  │  SensorPublisher │──────▶ │   Broker MQTT            │   │
│  │  (capteurs Java) │  MQTT  │   (Mosquitto :1883)      │   │
│  └──────────────────┘        └──────────┬───────────────┘   │
│                                         │ MQTT subscribe     │
│                               ┌─────────▼───────────────┐   │
│                               │   BackendServer          │   │
│                               │   (Java WebSocket :8080) │   │
│                               └─────────┬───────────────┘   │
│                                         │ WebSocket          │
│                               ┌─────────▼───────────────┐   │
│                               │   Dashboard Web          │   │
│                               │   (index.html)           │   │
│                               └─────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

### Topics MQTT utilisés

| Topic | Description | Format |
|---|---|---|
| `building/room1/temperature` | Température de la salle 1 | `Temp=<valeur>` |
| `building/room1/humidity` | Humidité de la salle 1 | `Humidity=<valeur>` |
| `building/room1/smoke` | Détection de fumée salle 1 | `Smoke=true/false` |

---

## 🛠️ Technologies utilisées

| Technologie | Version | Rôle |
|---|---|---|
| Java | 11+ | Langage principal |
| Maven | 3.6+ | Gestionnaire de dépendances |
| Eclipse Paho MQTT | 1.2.5 | Client MQTT Java |
| Java-WebSocket | 1.5.3 | Serveur WebSocket Java |
| Mosquitto | Latest | Broker MQTT |
| HTML / CSS / JS | — | Dashboard web |

---

## ✅ Prérequis

- **Java 11** ou supérieur installé ([télécharger](https://adoptium.net/))
- **Maven 3.6+** installé ([télécharger](https://maven.apache.org/download.cgi))
- **Mosquitto** (broker MQTT) installé et en cours d'exécution

### Installation de Mosquitto

**Windows :**
```bash
# Via winget
winget install mosquitto
# Puis démarrer le service
net start mosquitto
```

**Linux (Debian/Ubuntu) :**
```bash
sudo apt-get install mosquitto mosquitto-clients
sudo systemctl start mosquitto
```

**macOS :**
```bash
brew install mosquitto
brew services start mosquitto
```

---

## 📦 Installation

```bash
# Cloner le dépôt
git clone https://github.com/Sava/smart-building-mqtt.git
cd smart-building-mqtt

# Compiler le projet
mvn compile
```

---

## ⚙️ Configuration

Le projet utilise par défaut les paramètres suivants (modifiables directement dans le code source) :

| Paramètre | Valeur par défaut | Fichier |
|---|---|---|
| Broker MQTT | `tcp://localhost:1883` | `SensorPublisher.java`, `BackendServer.java` |
| Port WebSocket | `8080` | `BackendServer.java` |
| Topic MQTT | `building/+/+` | `BackendServer.java` |
| Intervalle capteurs | `3000 ms` | `SensorPublisher.java` |

> **Note :** Si votre broker MQTT tourne sur une autre machine ou un autre port, modifiez l'URL dans `BackendServer.java` et `SensorPublisher.java`.

---

## 🚀 Lancement

> ⚠️ **Assurez-vous que Mosquitto est en cours d'exécution avant de lancer les composants Java.**

### 1. Démarrer le serveur backend (pont MQTT → WebSocket)

```bash
mvn exec:java -Dexec.mainClass="BackendServer"
```

Vous devriez voir :
```
WebSocket serveur démarré !
WebSocket lancé sur ws://localhost:8080
```

### 2. Démarrer le simulateur de capteurs

Dans un **second terminal** :

```bash
mvn exec:java -Dexec.mainClass="SensorPublisher"
```

### 3. Ouvrir le dashboard web

Ouvrez le fichier dans votre navigateur :

```
src/main/java/front/index.html
```

Ou directement depuis l'explorateur de fichiers.

---

## 🖥️ Utilisation

Une fois les trois composants lancés :

1. Le dashboard affiche **✅ Connecté au serveur**
2. Les données des capteurs se mettent à jour **toutes les 3 secondes** :
   - 🌡️ Température : valeur aléatoire entre 18°C et 32°C
   - 💧 Humidité : valeur aléatoire entre 40% et 70%
   - 🔥 Fumée : détection aléatoire (1 chance sur 20)

Pour tester manuellement avec Mosquitto :

```bash
# Publier un message de test
mosquitto_pub -h localhost -t "building/room1/temperature" -m "Temp=25"

# S'abonner et observer les messages
mosquitto_sub -h localhost -t "building/+/+" -v
```

---

## 📸 Captures d'écran



---

## 📄 Licence

See the [`LICENSE`] file for the license and usage conditions.
