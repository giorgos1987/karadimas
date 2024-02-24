import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISupplier } from 'app/shared/model/supplier.model';
import { getEntities as getSuppliers } from 'app/entities/supplier/supplier.reducer';
import { ISupplierpayments } from 'app/shared/model/supplierpayments.model';
import { Paystatus } from 'app/shared/model/enumerations/paystatus.model';
import { getEntity, updateEntity, createEntity, reset } from './supplierpayments.reducer';

export const SupplierpaymentsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const suppliers = useAppSelector(state => state.supplier.entities);
  const supplierpaymentsEntity = useAppSelector(state => state.supplierpayments.entity);
  const loading = useAppSelector(state => state.supplierpayments.loading);
  const updating = useAppSelector(state => state.supplierpayments.updating);
  const updateSuccess = useAppSelector(state => state.supplierpayments.updateSuccess);
  const paystatusValues = Object.keys(Paystatus);

  const handleClose = () => {
    navigate('/supplierpayments' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSuppliers({}));
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
      ...supplierpaymentsEntity,
      ...values,
      supplier: suppliers.find(it => it.id.toString() === values.supplier.toString()),
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
          ...supplierpaymentsEntity,
          invoiceDate: convertDateTimeFromServer(supplierpaymentsEntity.invoiceDate),
          dueDate: convertDateTimeFromServer(supplierpaymentsEntity.dueDate),
          lastupdated: convertDateTimeFromServer(supplierpaymentsEntity.lastupdated),
          supplier: supplierpaymentsEntity?.supplier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="karadimastyresApp.supplierpayments.home.createOrEditLabel" data-cy="SupplierpaymentsCreateUpdateHeading">
            <Translate contentKey="karadimastyresApp.supplierpayments.home.createOrEditLabel">Create or edit a Supplierpayments</Translate>
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
                  id="supplierpayments-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('karadimastyresApp.supplierpayments.invoiceDate')}
                id="supplierpayments-invoiceDate"
                name="invoiceDate"
                data-cy="invoiceDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplierpayments.dueDate')}
                id="supplierpayments-dueDate"
                name="dueDate"
                data-cy="dueDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplierpayments.ispaid')}
                id="supplierpayments-ispaid"
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
                label={translate('karadimastyresApp.supplierpayments.amountDue')}
                id="supplierpayments-amountDue"
                name="amountDue"
                data-cy="amountDue"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplierpayments.notes')}
                id="supplierpayments-notes"
                name="notes"
                data-cy="notes"
                type="textarea"
              />
              <ValidatedBlobField
                label={translate('karadimastyresApp.supplierpayments.attachments')}
                id="supplierpayments-attachments"
                name="attachments"
                data-cy="attachments"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplierpayments.tax')}
                id="supplierpayments-tax"
                name="tax"
                data-cy="tax"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplierpayments.shipping')}
                id="supplierpayments-shipping"
                name="shipping"
                data-cy="shipping"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.supplierpayments.lastupdated')}
                id="supplierpayments-lastupdated"
                name="lastupdated"
                data-cy="lastupdated"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="supplierpayments-supplier"
                name="supplier"
                data-cy="supplier"
                label={translate('karadimastyresApp.supplierpayments.supplier')}
                type="select"
                required
              >
                <option value="" key="0" />
                {suppliers
                  ? suppliers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.company}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/supplierpayments" replace color="info">
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

export default SupplierpaymentsUpdate;
