import job from 'app/entities/job/job.reducer';
import jobCategory from 'app/entities/job-category/job-category.reducer';
import customerDetails from 'app/entities/customer-details/customer-details.reducer';
import cart from 'app/entities/cart/cart.reducer';
import supplierpayments from 'app/entities/supplierpayments/supplierpayments.reducer';
import booking from 'app/entities/booking/booking.reducer';
import supplier from 'app/entities/supplier/supplier.reducer';
import customerpayments from 'app/entities/customerpayments/customerpayments.reducer';
import statistics from 'app/entities/statistics/statistics.reducer';
import companyProfile from 'app/entities/company-profile/company-profile.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  job,
  jobCategory,
  customerDetails,
  cart,
  supplierpayments,
  booking,
  supplier,
  customerpayments,
  statistics,
  companyProfile,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
