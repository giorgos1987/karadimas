import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INeworders } from 'app/shared/model/neworders.model';
import { getEntity, updateEntity, createEntity, reset } from './neworders.reducer';

export const NewordersUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const newordersEntity = useAppSelector(state => state.neworders.entity);
  const loading = useAppSelector(state => state.neworders.loading);
  const updating = useAppSelector(state => state.neworders.updating);
  const updateSuccess = useAppSelector(state => state.neworders.updateSuccess);

  const handleClose = () => {
    navigate('/neworders' + location.search);
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
    values.orderDate = convertDateTimeToServer(values.orderDate);

    const entity = {
      ...newordersEntity,
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
          orderDate: displayDefaultDateTime(),
        }
      : {
          ...newordersEntity,
          orderDate: convertDateTimeFromServer(newordersEntity.orderDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="karadimastyresApp.neworders.home.createOrEditLabel" data-cy="NewordersCreateUpdateHeading">
            <Translate contentKey="karadimastyresApp.neworders.home.createOrEditLabel">Create or edit a Neworders</Translate>
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
                  id="neworders-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('karadimastyresApp.neworders.orderDate')}
                id="neworders-orderDate"
                name="orderDate"
                data-cy="orderDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('karadimastyresApp.neworders.elastika1')}
                id="neworders-elastika1"
                name="elastika1"
                data-cy="elastika1"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.neworders.elastika2')}
                id="neworders-elastika2"
                name="elastika2"
                data-cy="elastika2"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.neworders.elastika3')}
                id="neworders-elastika3"
                name="elastika3"
                data-cy="elastika3"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.neworders.elastika4')}
                id="neworders-elastika4"
                name="elastika4"
                data-cy="elastika4"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.neworders.elastika5')}
                id="neworders-elastika5"
                name="elastika5"
                data-cy="elastika5"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.neworders.elastika6')}
                id="neworders-elastika6"
                name="elastika6"
                data-cy="elastika6"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.neworders.elastika7')}
                id="neworders-elastika7"
                name="elastika7"
                data-cy="elastika7"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.neworders.elastika8')}
                id="neworders-elastika8"
                name="elastika8"
                data-cy="elastika8"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.neworders.elastika9')}
                id="neworders-elastika9"
                name="elastika9"
                data-cy="elastika9"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.neworders.elastika10')}
                id="neworders-elastika10"
                name="elastika10"
                data-cy="elastika10"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.neworders.elastika11')}
                id="neworders-elastika11"
                name="elastika11"
                data-cy="elastika11"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.neworders.elastika12')}
                id="neworders-elastika12"
                name="elastika12"
                data-cy="elastika12"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.neworders.elastika13')}
                id="neworders-elastika13"
                name="elastika13"
                data-cy="elastika13"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/neworders" replace color="info">
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

export default NewordersUpdate;
