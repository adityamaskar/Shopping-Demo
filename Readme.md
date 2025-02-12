Order Service → Publishes OrderCreated event to Kafka.
Inventory Service → Consumes OrderCreated, checks stock, and publishes InventoryReserved or InventoryFailed.
Payment Service → Consumes InventoryReserved, processes payment, and publishes PaymentProcessed or PaymentFailed.
Order Service → Receives PaymentProcessed or rollback event to mark order as completed or canceled.


Steps to run whole setup on the AKS : -
1. create AKS cluster
2. Get kafka relevant stuff on the aks.
   helm repo add strimzi https://strimzi.io/charts/
   helm repo update
   helm install strimzi-kafka-operator strimzi/strimzi-kafka-operator

3. Get static IP on the azure
   az aks show --resource-group aditya-maskar --name aks-cluster --query nodeResourceGroup -o tsv

   az network public-ip create --resource-group MC_aditya-maskar_aks-cluster_centralindia --name myAKSPublicIPForIngress --sku Standard --allocation-method static --query publicIp.ipAddress -o tsv

    u'll get IP

4. ingress stuff
   kubectl create namespace ingress-basic
   helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx

   helm install ingress-nginx ingress-nginx/ingress-nginx    --namespace ingress-basic    --set controller.replicaCount=1    --set controller.nodeSelector."kubernetes\.io/os"=linux    --set defaultBackend.nodeSelector."kubernetes\.io/os"=linux    --set controller.service.externalTrafficPolicy=Local --set controller.service.loadBalancerIP="74.225.179.49"
5. then deploy whole all yaml files.

    
