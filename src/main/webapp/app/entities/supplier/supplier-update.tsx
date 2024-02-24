import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISupplier } from 'app/shared/model/supplier.model';
import { getEntity, updateEntity, createEntity, reset } from './supplier.reducer';

export const SupplierUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const supplierEntity = useAppSelector(state => state.supplier.entity);
  const loading = useAppSelector(state => state.supplier.loading);
  const updating = useAppSelector(state => state.supplier.updating);
  const updateSuccess = useAppSelector(state => state.supplier.updateSuccess);

  const handleClose = () => {
    navigate('/supplier' + location.search);
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
    const entity = {
      ...supplierEntity,
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
      ? {}
      : {
          ...supplierEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="karadimastyresApp.supplier.home.createOrEditLabel" data-cy="SupplierCreateUpdateHeading">
            <Translate contentKey="karadimastyresApp.supplier.home.createOrEditLabel">Create or edit a Supplier</Translate>
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
                  id="supplier-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('karadimastyresApp.supplier.company')}
                id="supplier-company"
                name="company"
                data-cy="company"
                type="text"
                validate={{
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.webPage')}
                id="supplier-webPage"
                name="webPage"
                data-cy="webPage"
                type="textarea"
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.notes')}
                id="supplier-notes"
                name="notes"
                data-cy="notes"
                type="textarea"
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.lastName')}
                id="supplier-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
                validate={{
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.firstName')}
                id="supplier-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
                validate={{
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.emailAddress')}
                id="supplier-emailAddress"
                name="emailAddress"
                data-cy="emailAddress"
                type="text"
                validate={{
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.jobTitle')}
                id="supplier-jobTitle"
                name="jobTitle"
                data-cy="jobTitle"
                type="text"
                validate={{
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.businessPhone')}
                id="supplier-businessPhone"
                name="businessPhone"
                data-cy="businessPhone"
                type="text"
                validate={{
                  maxLength: { value: 25, message: translate('entity.validation.maxlength', { max: 25 }) },
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.homePhone')}
                id="supplier-homePhone"
                name="homePhone"
                data-cy="homePhone"
                type="text"
                validate={{
                  maxLength: { value: 25, message: translate('entity.validation.maxlength', { max: 25 }) },
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.mobilePhone')}
                id="supplier-mobilePhone"
                name="mobilePhone"
                data-cy="mobilePhone"
                type="text"
                validate={{
                  maxLength: { value: 25, message: translate('entity.validation.maxlength', { max: 25 }) },
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.faxNumber')}
                id="supplier-faxNumber"
                name="faxNumber"
                data-cy="faxNumber"
                type="text"
                validate={{
                  maxLength: { value: 25, message: translate('entity.validation.maxlength', { max: 25 }) },
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.address')}
                id="supplier-address"
                name="address"
                data-cy="address"
                type="textarea"
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.city')}
                id="supplier-city"
                name="city"
                data-cy="city"
                type="text"
                validate={{
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.stateProvince')}
                id="supplier-stateProvince"
                name="stateProvince"
                data-cy="stateProvince"
                type="text"
                validate={{
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.zipPostalCode')}
                id="supplier-zipPostalCode"
                name="zipPostalCode"
                data-cy="zipPostalCode"
                type="text"
                validate={{
                  maxLength: { value: 15, message: translate('entity.validation.maxlength', { max: 15 }) },
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplier.countryRegion')}
                id="supplier-countryRegion"
                name="countryRegion"
                data-cy="countryRegion"
                type="text"
                validate={{
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedBlobField
                label={translate('karadimastyresApp.supplier.attachments')}
                id="supplier-attachments"
                name="attachments"
                data-cy="attachments"
                openActionLabel={translate('entity.action.open')}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/supplier" replace color="info">
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

export default SupplierUpdate;
