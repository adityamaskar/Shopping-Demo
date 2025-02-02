import "./App.css";
import CustomerList from "./Components/CustomerList";
import OrderForm from "./Components/OrderFrom";
import OrderList from "./Components/OrderList";
import ProductList from "./Components/ProductList";

function App() {
  return (
    <>
      <OrderList />
      <ProductList />
      <CustomerList />
      <OrderForm />
    </>
  );
}

export default App;
