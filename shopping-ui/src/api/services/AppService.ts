import axiosInstance from "../axios";
import { Customer, Order, Product } from "../../types"; // Define your types
import { OrderFormData } from "../../Components/OrderFrom";

class OrderService {
  baseUrl = import.meta.env.VITE_BASE_URL || "/";
  // Fetch all orders
  async getOrders(): Promise<Order[]> {
    const response = await axiosInstance.get(
      this.baseUrl + "/order/get-orders"
    );
    console.log(response);
    return response.data;
  }

  // Place a new order
  async placeOrder(order: OrderFormData): Promise<Order> {
    const response = await axiosInstance.post(
      this.baseUrl + "/order/place-order",
      order
    );
    return response.data;
  }

  async getProducts(): Promise<Product[]> {
    const response = await axiosInstance.get(
      this.baseUrl + "/inventory/products"
    );
    console.log(response);
    return response.data;
  }

  async getCustomers(): Promise<Customer[]> {
    const response = await axiosInstance.get(this.baseUrl + "/customer/all");
    console.log(response);
    return response.data;
  }
}

export default new OrderService();
