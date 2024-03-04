import cart from 'app/entities/cart/cart.reducer';
import customers from 'app/entities/customers/customers.reducer';
import job from 'app/entities/job/job.reducer';
import neworders from 'app/entities/neworders/neworders.reducer';
import customerpayments from 'app/entities/customerpayments/customerpayments.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  cart,
  customers,
  job,
  neworders,
  customerpayments,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
