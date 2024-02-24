import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './supplier.reducer';

export const SupplierDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const supplierEntity = useAppSelector(state => state.supplier.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="supplierDetailsHeading">
          <Translate contentKey="karadimastyresApp.supplier.detail.title">Supplier</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.id}</dd>
          <dt>
            <span id="company">
              <Translate contentKey="karadimastyresApp.supplier.company">Company</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.company}</dd>
          <dt>
            <span id="webPage">
              <Translate contentKey="karadimastyresApp.supplier.webPage">Web Page</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.webPage}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="karadimastyresApp.supplier.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.notes}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="karadimastyresApp.supplier.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.lastName}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="karadimastyresApp.supplier.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.firstName}</dd>
          <dt>
            <span id="emailAddress">
              <Translate contentKey="karadimastyresApp.supplier.emailAddress">Email Address</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.emailAddress}</dd>
          <dt>
            <span id="jobTitle">
              <Translate contentKey="karadimastyresApp.supplier.jobTitle">Job Title</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.jobTitle}</dd>
          <dt>
            <span id="businessPhone">
              <Translate contentKey="karadimastyresApp.supplier.businessPhone">Business Phone</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.businessPhone}</dd>
          <dt>
            <span id="homePhone">
              <Translate contentKey="karadimastyresApp.supplier.homePhone">Home Phone</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.homePhone}</dd>
          <dt>
            <span id="mobilePhone">
              <Translate contentKey="karadimastyresApp.supplier.mobilePhone">Mobile Phone</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.mobilePhone}</dd>
          <dt>
            <span id="faxNumber">
              <Translate contentKey="karadimastyresApp.supplier.faxNumber">Fax Number</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.faxNumber}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="karadimastyresApp.supplier.address">Address</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.address}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="karadimastyresApp.supplier.city">City</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.city}</dd>
          <dt>
            <span id="stateProvince">
              <Translate contentKey="karadimastyresApp.supplier.stateProvince">State Province</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.stateProvince}</dd>
          <dt>
            <span id="zipPostalCode">
              <Translate contentKey="karadimastyresApp.supplier.zipPostalCode">Zip Postal Code</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.zipPostalCode}</dd>
          <dt>
            <span id="countryRegion">
              <Translate contentKey="karadimastyresApp.supplier.countryRegion">Country Region</Translate>
            </span>
          </dt>
          <dd>{supplierEntity.countryRegion}</dd>
          <dt>
            <span id="attachments">
              <Translate contentKey="karadimastyresApp.supplier.attachments">Attachments</Translate>
            </span>
          </dt>
          <dd>
            {supplierEntity.attachments ? (
              <div>
                {supplierEntity.attachmentsContentType ? (
                  <a onClick={openFile(supplierEntity.attachmentsContentType, supplierEntity.attachments)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {supplierEntity.attachmentsContentType}, {byteSize(supplierEntity.attachments)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/supplier" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/supplier/${supplierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SupplierDetail;
