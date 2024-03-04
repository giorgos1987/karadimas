import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICustomers } from 'app/shared/model/customers.model';
import { Typeofcustomer } from 'app/shared/model/enumerations/typeofcustomer.model';
import { getEntity, updateEntity, createEntity, reset } from './customers.reducer';

export const CustomersUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customersEntity = useAppSelector(state => state.customers.entity);
  const loading = useAppSelector(state => state.customers.loading);
  const updating = useAppSelector(state => state.customers.updating);
  const updateSuccess = useAppSelector(state => state.customers.updateSuccess);
  const typeofcustomerValues = Object.keys(Typeofcustomer);

  const handleClose = () => {
    navigate('/customers' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.lastseen = convertDateTimeToServer(values.lastseen);
    values.firstseen = convertDateTimeToServer(values.firstseen);

    const entity = {
      ...customersEntity,
      ...values,
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
          lastseen: displayDefaultDateTime(),
          firstseen: displayDefaultDateTime(),
        }
      : {
          customertype: 'PRIVATE',
          ...customersEntity,
          lastseen: convertDateTimeFromServer(customersEntity.lastseen),
          firstseen: convertDateTimeFromServer(customersEntity.firstseen),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="karadimastyresApp.customers.home.createOrEditLabel" data-cy="CustomersCreateUpdateHeading">
            <Translate contentKey="karadimastyresApp.customers.home.createOrEditLabel">Create or edit a Customers</Translate>
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
                  id="customers-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('karadimastyresApp.customers.name')}
                id="customers-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customers.car')}
                id="customers-car"
                name="car"
                data-cy="car"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customers.notes')}
                id="customers-notes"
                name="notes"
                data-cy="notes"
                type="textarea"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customers.phone')}
                id="customers-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customers.tyres')}
                id="customers-tyres"
                name="tyres"
                data-cy="tyres"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customers.plates')}
                id="customers-plates"
                name="plates"
                data-cy="plates"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customers.customertype')}
                id="customers-customertype"
                name="customertype"
                data-cy="customertype"
                type="select"
              >
                {typeofcustomerValues.map(typeofcustomer => (
                  <option value={typeofcustomer} key={typeofcustomer}>
                    {translate('karadimastyresApp.Typeofcustomer.' + typeofcustomer)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('karadimastyresApp.customers.lastseen')}
                id="customers-lastseen"
                name="lastseen"
                data-cy="lastseen"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customers.firstseen')}
                id="customers-firstseen"
                name="firstseen"
                data-cy="firstseen"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customers.proselesysis')}
                id="customers-proselesysis"
                name="proselesysis"
                data-cy="proselesysis"
                type="textarea"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customers.mobile')}
                id="customers-mobile"
                name="mobile"
                data-cy="mobile"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customers.companyphone')}
                id="customers-companyphone"
                name="companyphone"
                data-cy="companyphone"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customers.email')}
                id="customers-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customers.addressLine')}
                id="customers-addressLine"
                name="addressLine"
                data-cy="addressLine"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customers.cityCountry')}
                id="customers-cityCountry"
                name="cityCountry"
                data-cy="cityCountry"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/customers" replace color="info">
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

export default CustomersUpdate;
