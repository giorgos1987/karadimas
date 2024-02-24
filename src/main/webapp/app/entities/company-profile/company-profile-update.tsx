import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICompanyProfile } from 'app/shared/model/company-profile.model';
import { getEntity, updateEntity, createEntity, reset } from './company-profile.reducer';

export const CompanyProfileUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const companyProfileEntity = useAppSelector(state => state.companyProfile.entity);
  const loading = useAppSelector(state => state.companyProfile.loading);
  const updating = useAppSelector(state => state.companyProfile.updating);
  const updateSuccess = useAppSelector(state => state.companyProfile.updateSuccess);

  const handleClose = () => {
    navigate('/company-profile');
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
      ...companyProfileEntity,
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
          ...companyProfileEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="karadimastyresApp.companyProfile.home.createOrEditLabel" data-cy="CompanyProfileCreateUpdateHeading">
            <Translate contentKey="karadimastyresApp.companyProfile.home.createOrEditLabel">Create or edit a CompanyProfile</Translate>
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
                  id="company-profile-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('karadimastyresApp.companyProfile.companyName')}
                id="company-profile-companyName"
                name="companyName"
                data-cy="companyName"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.companyProfile.address1')}
                id="company-profile-address1"
                name="address1"
                data-cy="address1"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.companyProfile.address2')}
                id="company-profile-address2"
                name="address2"
                data-cy="address2"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.companyProfile.suburb')}
                id="company-profile-suburb"
                name="suburb"
                data-cy="suburb"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.companyProfile.state')}
                id="company-profile-state"
                name="state"
                data-cy="state"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.companyProfile.postcode')}
                id="company-profile-postcode"
                name="postcode"
                data-cy="postcode"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.companyProfile.phone')}
                id="company-profile-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.companyProfile.mobile')}
                id="company-profile-mobile"
                name="mobile"
                data-cy="mobile"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.companyProfile.email')}
                id="company-profile-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('karadimastyresApp.companyProfile.emailInfo')}
                id="company-profile-emailInfo"
                name="emailInfo"
                data-cy="emailInfo"
                type="text"
              />
              <ValidatedBlobField
                label={translate('karadimastyresApp.companyProfile.logo')}
                id="company-profile-logo"
                name="logo"
                data-cy="logo"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('karadimastyresApp.companyProfile.language')}
                id="company-profile-language"
                name="language"
                data-cy="language"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/company-profile" replace color="info">
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

export default CompanyProfileUpdate;
