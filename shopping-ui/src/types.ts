// Order-related types
export interface Order {
  id: number;
  productId: number;
  quantity: number;
  customerId: number;
  status: string;
}

export interface OrderDTO {
  productId: number;
  quantity: number;
  customerId: number;
}

// Product-related types
export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  quantityInStock: number;
}

// Customer-related types
export interface Customer {
  id: number;
  name: string;
  email: string;
  phone: string;
  address: string;
  city: string;
  balanceAmount: number;
}
