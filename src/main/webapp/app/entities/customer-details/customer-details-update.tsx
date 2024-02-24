import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICustomerDetails } from 'app/shared/model/customer-details.model';
import { Typeofcustomer } from 'app/shared/model/enumerations/typeofcustomer.model';
import { getEntity, updateEntity, createEntity, reset } from './customer-details.reducer';

export const CustomerDetailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customerDetailsEntity = useAppSelector(state => state.customerDetails.entity);
  const loading = useAppSelector(state => state.customerDetails.loading);
  const updating = useAppSelector(state => state.customerDetails.updating);
  const updateSuccess = useAppSelector(state => state.customerDetails.updateSuccess);
  const typeofcustomerValues = Object.keys(Typeofcustomer);

  const handleClose = () => {
    navigate('/customer-details' + location.search);
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
      ...customerDetailsEntity,
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
          ...customerDetailsEntity,
          lastseen: convertDateTimeFromServer(customerDetailsEntity.lastseen),
          firstseen: convertDateTimeFromServer(customerDetailsEntity.firstseen),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="karadimastyresApp.customerDetails.home.createOrEditLabel" data-cy="CustomerDetailsCreateUpdateHeading">
            <Translate contentKey="karadimastyresApp.customerDetails.home.createOrEditLabel">Create or edit a CustomerDetails</Translate>
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
                  id="customer-details-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.name')}
                id="customer-details-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.plates')}
                id="customer-details-plates"
                name="plates"
                data-cy="plates"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.customertype')}
                id="customer-details-customertype"
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
                label={translate('karadimastyresApp.customerDetails.lastseen')}
                id="customer-details-lastseen"
                name="lastseen"
                data-cy="lastseen"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.firstseen')}
                id="customer-details-firstseen"
                name="firstseen"
                data-cy="firstseen"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.proselesysis')}
                id="customer-details-proselesysis"
                name="proselesysis"
                data-cy="proselesysis"
                type="textarea"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.mobile')}
                id="customer-details-mobile"
                name="mobile"
                data-cy="mobile"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.phone')}
                id="customer-details-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.companyphone')}
                id="customer-details-companyphone"
                name="companyphone"
                data-cy="companyphone"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.email')}
                id="customer-details-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.notes')}
                id="customer-details-notes"
                name="notes"
                data-cy="notes"
                type="textarea"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.addressLine1')}
                id="customer-details-addressLine1"
                name="addressLine1"
                data-cy="addressLine1"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.addressLine2')}
                id="customer-details-addressLine2"
                name="addressLine2"
                data-cy="addressLine2"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.city')}
                id="customer-details-city"
                name="city"
                data-cy="city"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerDetails.country')}
                id="customer-details-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/customer-details" replace color="info">
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

export default CustomerDetailsUpdate;
