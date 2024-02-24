import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICustomerDetails } from 'app/shared/model/customer-details.model';
import { getEntities as getCustomerDetails } from 'app/entities/customer-details/customer-details.reducer';
import { ICustomerpayments } from 'app/shared/model/customerpayments.model';
import { Paystatus } from 'app/shared/model/enumerations/paystatus.model';
import { getEntity, updateEntity, createEntity, reset } from './customerpayments.reducer';

export const CustomerpaymentsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customerDetails = useAppSelector(state => state.customerDetails.entities);
  const customerpaymentsEntity = useAppSelector(state => state.customerpayments.entity);
  const loading = useAppSelector(state => state.customerpayments.loading);
  const updating = useAppSelector(state => state.customerpayments.updating);
  const updateSuccess = useAppSelector(state => state.customerpayments.updateSuccess);
  const paystatusValues = Object.keys(Paystatus);

  const handleClose = () => {
    navigate('/customerpayments' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCustomerDetails({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.invoiceDate = convertDateTimeToServer(values.invoiceDate);
    values.dueDate = convertDateTimeToServer(values.dueDate);
    values.lastupdated = convertDateTimeToServer(values.lastupdated);

    const entity = {
      ...customerpaymentsEntity,
      ...values,
      customerDetails: customerDetails.find(it => it.id.toString() === values.customerDetails.toString()),
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
          invoiceDate: displayDefaultDateTime(),
          dueDate: displayDefaultDateTime(),
          lastupdated: displayDefaultDateTime(),
        }
      : {
          ispaid: 'YES',
          ...customerpaymentsEntity,
          invoiceDate: convertDateTimeFromServer(customerpaymentsEntity.invoiceDate),
          dueDate: convertDateTimeFromServer(customerpaymentsEntity.dueDate),
          lastupdated: convertDateTimeFromServer(customerpaymentsEntity.lastupdated),
          customerDetails: customerpaymentsEntity?.customerDetails?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="karadimastyresApp.customerpayments.home.createOrEditLabel" data-cy="CustomerpaymentsCreateUpdateHeading">
            <Translate contentKey="karadimastyresApp.customerpayments.home.createOrEditLabel">Create or edit a Customerpayments</Translate>
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
                  id="customerpayments-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('karadimastyresApp.customerpayments.ispaid')}
                id="customerpayments-ispaid"
                name="ispaid"
                data-cy="ispaid"
                type="select"
              >
                {paystatusValues.map(paystatus => (
                  <option value={paystatus} key={paystatus}>
                    {translate('karadimastyresApp.Paystatus.' + paystatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('karadimastyresApp.customerpayments.invoiceDate')}
                id="customerpayments-invoiceDate"
                name="invoiceDate"
                data-cy="invoiceDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerpayments.dueDate')}
                id="customerpayments-dueDate"
                name="dueDate"
                data-cy="dueDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerpayments.tax')}
                id="customerpayments-tax"
                name="tax"
                data-cy="tax"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerpayments.shipping')}
                id="customerpayments-shipping"
                name="shipping"
                data-cy="shipping"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerpayments.amountDue')}
                id="customerpayments-amountDue"
                name="amountDue"
                data-cy="amountDue"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerpayments.notes')}
                id="customerpayments-notes"
                name="notes"
                data-cy="notes"
                type="textarea"
              />
              <ValidatedBlobField
                label={translate('karadimastyresApp.customerpayments.attachments')}
                id="customerpayments-attachments"
                name="attachments"
                data-cy="attachments"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('karadimastyresApp.customerpayments.lastupdated')}
                id="customerpayments-lastupdated"
                name="lastupdated"
                data-cy="lastupdated"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="customerpayments-customerDetails"
                name="customerDetails"
                data-cy="customerDetails"
                label={translate('karadimastyresApp.customerpayments.customerDetails')}
                type="select"
                required
              >
                <option value="" key="0" />
                {customerDetails
                  ? customerDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/customerpayments" replace color="info">
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

export default CustomerpaymentsUpdate;
