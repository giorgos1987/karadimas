import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJob } from 'app/shared/model/job.model';
import { getEntities as getJobs } from 'app/entities/job/job.reducer';
import { ICustomers } from 'app/shared/model/customers.model';
import { getEntities as getCustomers } from 'app/entities/customers/customers.reducer';
import { ICart } from 'app/shared/model/cart.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';
import { getEntity, updateEntity, createEntity, reset } from './cart.reducer';

export const CartUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const jobs = useAppSelector(state => state.job.entities);
  const customers = useAppSelector(state => state.customers.entities);
  const cartEntity = useAppSelector(state => state.cart.entity);
  const loading = useAppSelector(state => state.cart.loading);
  const updating = useAppSelector(state => state.cart.updating);
  const updateSuccess = useAppSelector(state => state.cart.updateSuccess);
  const orderStatusValues = Object.keys(OrderStatus);

  const handleClose = () => {
    navigate('/cart' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getJobs({}));
    dispatch(getCustomers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.placeddate = convertDateTimeToServer(values.placeddate);
    values.scheduleddate = convertDateTimeToServer(values.scheduleddate);
    values.deliverydate = convertDateTimeToServer(values.deliverydate);

    const entity = {
      ...cartEntity,
      ...values,
      jobs: mapIdList(values.jobs),
      customers: customers.find(it => it.id.toString() === values.customers.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          placeddate: displayDefaultDateTime(),
          scheduleddate: displayDefaultDateTime(),
          deliverydate: displayDefaultDateTime(),
        }
      : {
          status: 'PENDING',
          ...cartEntity,
          placeddate: convertDateTimeFromServer(cartEntity.placeddate),
          scheduleddate: convertDateTimeFromServer(cartEntity.scheduleddate),
          deliverydate: convertDateTimeFromServer(cartEntity.deliverydate),
          jobs: cartEntity?.jobs?.map(e => e.id.toString()),
          customers: cartEntity?.customers?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="karadimastyresApp.cart.home.createOrEditLabel" data-cy="CartCreateUpdateHeading">
            <Translate contentKey="karadimastyresApp.cart.home.createOrEditLabel">Create or edit a Cart</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="cart-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('karadimastyresApp.cart.name')} id="cart-name" name="name" data-cy="name" type="text" />
              <ValidatedField label={translate('karadimastyresApp.cart.plate')} id="cart-plate" name="plate" data-cy="plate" type="text" />
              <ValidatedField
                label={translate('karadimastyresApp.cart.placeddate')}
                id="cart-placeddate"
                name="placeddate"
                data-cy="placeddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('karadimastyresApp.cart.scheduleddate')}
                id="cart-scheduleddate"
                name="scheduleddate"
                data-cy="scheduleddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('karadimastyresApp.cart.deliverydate')}
                id="cart-deliverydate"
                name="deliverydate"
                data-cy="deliverydate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('karadimastyresApp.cart.brand')} id="cart-brand" name="brand" data-cy="brand" type="text" />
              <ValidatedField label={translate('karadimastyresApp.cart.model')} id="cart-model" name="model" data-cy="model" type="text" />
              <ValidatedField
                label={translate('karadimastyresApp.cart.numbertyres')}
                id="cart-numbertyres"
                name="numbertyres"
                data-cy="numbertyres"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.cart.status')}
                id="cart-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {orderStatusValues.map(orderStatus => (
                  <option value={orderStatus} key={orderStatus}>
                    {translate('karadimastyresApp.OrderStatus.' + orderStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('karadimastyresApp.cart.notes')}
                id="cart-notes"
                name="notes"
                data-cy="notes"
                type="textarea"
              />
              <ValidatedField
                label={translate('karadimastyresApp.cart.mechanic')}
                id="cart-mechanic"
                name="mechanic"
                data-cy="mechanic"
                type="text"
              />
              <ValidatedField label={translate('karadimastyresApp.cart.phone')} id="cart-phone" name="phone" data-cy="phone" type="text" />
              <ValidatedField
                label={translate('karadimastyresApp.cart.address')}
                id="cart-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.cart.workorder')}
                id="cart-workorder"
                name="workorder"
                data-cy="workorder"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.cart.kilometers')}
                id="cart-kilometers"
                name="kilometers"
                data-cy="kilometers"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.cart.paymentMethod')}
                id="cart-paymentMethod"
                name="paymentMethod"
                data-cy="paymentMethod"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.cart.totalPrice')}
                id="cart-totalPrice"
                name="totalPrice"
                data-cy="totalPrice"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.cart.job')}
                id="cart-job"
                data-cy="job"
                type="select"
                multiple
                name="jobs"
              >
                <option value="" key="0" />
                {jobs
                  ? jobs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="cart-customers"
                name="customers"
                data-cy="customers"
                label={translate('karadimastyresApp.cart.customers')}
                type="select"
                required
              >
                <option value="" key="0" />
                {customers
                  ? customers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cart" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CartUpdate;
