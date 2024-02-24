import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStatistics } from 'app/shared/model/statistics.model';
import { getEntity, updateEntity, createEntity, reset } from './statistics.reducer';

export const StatisticsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const statisticsEntity = useAppSelector(state => state.statistics.entity);
  const loading = useAppSelector(state => state.statistics.loading);
  const updating = useAppSelector(state => state.statistics.updating);
  const updateSuccess = useAppSelector(state => state.statistics.updateSuccess);

  const handleClose = () => {
    navigate('/statistics');
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
      ...statisticsEntity,
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
          ...statisticsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="karadimastyresApp.statistics.home.createOrEditLabel" data-cy="StatisticsCreateUpdateHeading">
            <Translate contentKey="karadimastyresApp.statistics.home.createOrEditLabel">Create or edit a Statistics</Translate>
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
                  id="statistics-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('karadimastyresApp.statistics.todaysales')}
                id="statistics-todaysales"
                name="todaysales"
                data-cy="todaysales"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.totalCustomersNumb')}
                id="statistics-totalCustomersNumb"
                name="totalCustomersNumb"
                data-cy="totalCustomersNumb"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.custmerTotal')}
                id="statistics-custmerTotal"
                name="custmerTotal"
                data-cy="custmerTotal"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.schedTotalNexWeek')}
                id="statistics-schedTotalNexWeek"
                name="schedTotalNexWeek"
                data-cy="schedTotalNexWeek"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.totalCarts')}
                id="statistics-totalCarts"
                name="totalCarts"
                data-cy="totalCarts"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.totalPending')}
                id="statistics-totalPending"
                name="totalPending"
                data-cy="totalPending"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.totalPayments')}
                id="statistics-totalPayments"
                name="totalPayments"
                data-cy="totalPayments"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.pendingPayments')}
                id="statistics-pendingPayments"
                name="pendingPayments"
                data-cy="pendingPayments"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.pendingNumberPayments')}
                id="statistics-pendingNumberPayments"
                name="pendingNumberPayments"
                data-cy="pendingNumberPayments"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.totalCars')}
                id="statistics-totalCars"
                name="totalCars"
                data-cy="totalCars"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.totalTrucks')}
                id="statistics-totalTrucks"
                name="totalTrucks"
                data-cy="totalTrucks"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.totalOther1')}
                id="statistics-totalOther1"
                name="totalOther1"
                data-cy="totalOther1"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.totalOther2')}
                id="statistics-totalOther2"
                name="totalOther2"
                data-cy="totalOther2"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.latestpayments')}
                id="statistics-latestpayments"
                name="latestpayments"
                data-cy="latestpayments"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.ltstsupplierpaym')}
                id="statistics-ltstsupplierpaym"
                name="ltstsupplierpaym"
                data-cy="ltstsupplierpaym"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.statistics.recentrlycompltd')}
                id="statistics-recentrlycompltd"
                name="recentrlycompltd"
                data-cy="recentrlycompltd"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/statistics" replace color="info">
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

export default StatisticsUpdate;
