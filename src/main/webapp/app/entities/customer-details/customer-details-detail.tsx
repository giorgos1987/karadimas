import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './customer-details.reducer';

export const CustomerDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const customerDetailsEntity = useAppSelector(state => state.customerDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerDetailsDetailsHeading">
          <Translate contentKey="karadimastyresApp.customerDetails.detail.title">CustomerDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="karadimastyresApp.customerDetails.name">Name</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.name}</dd>
          <dt>
            <span id="plates">
              <Translate contentKey="karadimastyresApp.customerDetails.plates">Plates</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.plates}</dd>
          <dt>
            <span id="customertype">
              <Translate contentKey="karadimastyresApp.customerDetails.customertype">Customertype</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.customertype}</dd>
          <dt>
            <span id="lastseen">
              <Translate contentKey="karadimastyresApp.customerDetails.lastseen">Lastseen</Translate>
            </span>
          </dt>
          <dd>
            {customerDetailsEntity.lastseen ? (
              <TextFormat value={customerDetailsEntity.lastseen} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="firstseen">
              <Translate contentKey="karadimastyresApp.customerDetails.firstseen">Firstseen</Translate>
            </span>
          </dt>
          <dd>
            {customerDetailsEntity.firstseen ? (
              <TextFormat value={customerDetailsEntity.firstseen} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="proselesysis">
              <Translate contentKey="karadimastyresApp.customerDetails.proselesysis">Proselesysis</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.proselesysis}</dd>
          <dt>
            <span id="mobile">
              <Translate contentKey="karadimastyresApp.customerDetails.mobile">Mobile</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.mobile}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="karadimastyresApp.customerDetails.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.phone}</dd>
          <dt>
            <span id="companyphone">
              <Translate contentKey="karadimastyresApp.customerDetails.companyphone">Companyphone</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.companyphone}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="karadimastyresApp.customerDetails.email">Email</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.email}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="karadimastyresApp.customerDetails.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.notes}</dd>
          <dt>
            <span id="addressLine1">
              <Translate contentKey="karadimastyresApp.customerDetails.addressLine1">Address Line 1</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.addressLine1}</dd>
          <dt>
            <span id="addressLine2">
              <Translate contentKey="karadimastyresApp.customerDetails.addressLine2">Address Line 2</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.addressLine2}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="karadimastyresApp.customerDetails.city">City</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.city}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="karadimastyresApp.customerDetails.country">Country</Translate>
            </span>
          </dt>
          <dd>{customerDetailsEntity.country}</dd>
        </dl>
        <Button tag={Link} to="/customer-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer-details/${customerDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerDetailsDetail;
