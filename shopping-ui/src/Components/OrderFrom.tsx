import React, { useState } from "react";
import AppService from "../api/services/AppService";

// Step 1: Define the interface for the form data
export interface OrderFormData {
  productId: number | null;
  quantity: number | null;
  customerId: number | null;
}

const OrderForm: React.FC = () => {
  // Step 2: Use useState to manage form state
  const [formData, setFormData] = useState<OrderFormData>({
    productId: null,
    quantity: null,
    customerId: null,
  });

  // Step 3: Handle input changes
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value ? Number(value) : null, // Convert input value to number
    });
  };

  // Step 4: Handle form submission
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Form Data Submitted:", formData);
    AppService.placeOrder(formData);
    // Add your form submission logic here (e.g., API call)
  };

  return (
    <div className="container my-3">
      <h2 className="my-3 d-flex" style={{ justifyContent: "center" }}>
        Create Order
      </h2>
      <form onSubmit={handleSubmit}>
        {/* Product ID Field */}
        <div className="mb-3">
          <label htmlFor="productId" className="form-label">
            Product ID
          </label>
          <input
            type="number"
            className="form-control"
            id="productId"
            name="productId"
            value={formData.productId ?? ""}
            onChange={handleInputChange}
            required
          />
        </div>

        {/* Quantity Field */}
        <div className="mb-3">
          <label htmlFor="quantity" className="form-label">
            Quantity
          </label>
          <input
            type="number"
            className="form-control"
            id="quantity"
            name="quantity"
            value={formData.quantity ?? ""}
            onChange={handleInputChange}
            required
          />
        </div>

        {/* Customer ID Field */}
        <div className="mb-3">
          <label htmlFor="customerId" className="form-label">
            Customer ID
          </label>
          <input
            type="number"
            className="form-control"
            id="customerId"
            name="customerId"
            value={formData.customerId ?? ""}
            onChange={handleInputChange}
            required
          />
        </div>

        {/* Submit Button */}
        <button type="submit" className="btn btn-primary">
          Submit
        </button>
      </form>
    </div>
  );
};

export default OrderForm;
