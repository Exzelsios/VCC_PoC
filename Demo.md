# Demonstration Kubernetes VCC-PoC
Verschiedene Aspekte von K8s demonstrierbar
+ Deploy Anwendung auf K8s - ``kubectl create -f ...``
+ Erstellen neuer Datapool durch Admin Panel - ``Create new, testdaten``
+ Erzeugen von Last durch debug Interface - ``vcc.../client/debug``
+ Automatisches skalieren durch HPA - ``kubectl get all``
+ Neue Version deployen - ``kubectl edit``

## Preparation
kubectl muss auf IBM Cluster "zeigen"
[Anleitung](https://console.bluemix.net/containers-kubernetes/clusters/6396509dff814d1ebd8e6d08b346002c/access?region=ibm:yp:eu-de)

## Deploy Anwendung
Beschreiben durch YAML Datei, Zwanghaft benötigt: Container Image (Docker)
```YAML
apiVersion: apps/v1
kind: Deployment
metadata:
  name: <NAME DEPLOY>
  labels:
    app: <SELECTOR>
spec:
  replicas: 1
  selector:
    matchLabels:
      app: <SELECTOR>
  template:
    metadata:
      labels:
        app: <SELECTOR Container Pods>
    spec:
      containers:
      - name: <CONTAINER PREFIX>
      image: <IMAGE NAME AND TAG ON DOCKERHUB>
```
YAML Datei speichern, dann mit ``kubectl create -f <DATEINAME>`` K8s Objekt erzeugen. Auch mehrere Objekte in einer YAML oder mehrere YAML als <DATEINAME> möglich (z.B. Ordner mit allen YAML Files).
## Erstellen neuer Datapool / Bereitstellen der Anwendung
- Admininterface VCC-PoC, create new, Filler Daten eingeben
- /client aufrufen, query

## Erzeugen Last / Kill eines Pods - Auto Scaling der Anwendung
- /client/debug aufrufen -> Start CPU Load
- Dabei ```watch kubectl get po,deploy,hpa```
-> Autoscaling sichtbar bei ansteigender CPU Last
- Stop CPU Load -> Downscaling sichtbar
- Kill eines Pods -> K8s scheduled neuen Pod automatisch

## Neue Version deployen
- Benötigt neues Container Image (ausreichend neuer Tag, nicht latest nutzen)
- ``kubectl edit deploy <DEPLOYNAME>`` -> Image ändern
- Eigentlich Build Pipeline außerhalb K8s
- ``watch kubectl get deploy,po`` -> Beobachten wie K8s neue Pods scheduled
