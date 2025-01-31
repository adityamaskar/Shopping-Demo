Order Service → Publishes OrderCreated event to Kafka.
Inventory Service → Consumes OrderCreated, checks stock, and publishes InventoryReserved or InventoryFailed.
Payment Service → Consumes InventoryReserved, processes payment, and publishes PaymentProcessed or PaymentFailed.
Order Service → Receives PaymentProcessed or rollback event to mark order as completed or canceled.