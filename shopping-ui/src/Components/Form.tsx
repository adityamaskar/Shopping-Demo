// This is the templete which we are using for the Form in whole application
import {
  UseFormRegister,
  DeepMap,
  FieldValues,
  FieldError,
  Path,
} from "react-hook-form";

interface Props<T extends FieldValues> {
  classNameOf: string;
  fieldId: Path<T>; // Change fieldId type to Path<T>
  formName: string;
  formType: string;
  funcName: string;
  register?: UseFormRegister<T>;
  errors?: DeepMap<FieldValues, FieldError>;
}

const Form = <T extends FieldValues>({
  classNameOf,
  fieldId,
  formName,
  formType,
  funcName,
  register,
  errors,
}: Props<T>) => {
  return (
    <div>
      <label htmlFor={String(fieldId)} className={classNameOf}>
        {" "}
        {/* Convert fieldId to string for htmlFor */}
        {formName}
      </label>
      <input
        {...(register ? register(fieldId) : {})}
        id={String(fieldId)}
        type={formType}
        className="form-control"
      />
      {errors && errors[String(fieldId)] && (
        // added the style here will correct it later
        <p style={{ color: "red" }}>{errors[String(fieldId)]?.message}</p>
      )}
    </div>
  );
};

export default Form;
