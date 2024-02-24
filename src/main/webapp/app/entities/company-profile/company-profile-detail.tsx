import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './company-profile.reducer';

export const CompanyProfileDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const companyProfileEntity = useAppSelector(state => state.companyProfile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyProfileDetailsHeading">
          <Translate contentKey="karadimastyresApp.companyProfile.detail.title">CompanyProfile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.id}</dd>
          <dt>
            <span id="companyName">
              <Translate contentKey="karadimastyresApp.companyProfile.companyName">Company Name</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.companyName}</dd>
          <dt>
            <span id="address1">
              <Translate contentKey="karadimastyresApp.companyProfile.address1">Address 1</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.address1}</dd>
          <dt>
            <span id="address2">
              <Translate contentKey="karadimastyresApp.companyProfile.address2">Address 2</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.address2}</dd>
          <dt>
            <span id="suburb">
              <Translate contentKey="karadimastyresApp.companyProfile.suburb">Suburb</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.suburb}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="karadimastyresApp.companyProfile.state">State</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.state}</dd>
          <dt>
            <span id="postcode">
              <Translate contentKey="karadimastyresApp.companyProfile.postcode">Postcode</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.postcode}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="karadimastyresApp.companyProfile.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.phone}</dd>
          <dt>
            <span id="mobile">
              <Translate contentKey="karadimastyresApp.companyProfile.mobile">Mobile</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.mobile}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="karadimastyresApp.companyProfile.email">Email</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.email}</dd>
          <dt>
            <span id="emailInfo">
              <Translate contentKey="karadimastyresApp.companyProfile.emailInfo">Email Info</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.emailInfo}</dd>
          <dt>
            <span id="logo">
              <Translate contentKey="karadimastyresApp.companyProfile.logo">Logo</Translate>
            </span>
          </dt>
          <dd>
            {companyProfileEntity.logo ? (
              <div>
                {companyProfileEntity.logoContentType ? (
                  <a onClick={openFile(companyProfileEntity.logoContentType, companyProfileEntity.logo)}>
                    <img
                      src={`data:${companyProfileEntity.logoContentType};base64,${companyProfileEntity.logo}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {companyProfileEntity.logoContentType}, {byteSize(companyProfileEntity.logo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="language">
              <Translate contentKey="karadimastyresApp.companyProfile.language">Language</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/company-profile" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company-profile/${companyProfileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompanyProfileDetail;
