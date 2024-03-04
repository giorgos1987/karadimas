import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './customers.reducer';

export const CustomersDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const customersEntity = useAppSelector(state => state.customers.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customersDetailsHeading">
          <Translate contentKey="karadimastyresApp.customers.detail.title">Customers</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customersEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="karadimastyresApp.customers.name">Name</Translate>
            </span>
          </dt>
          <dd>{customersEntity.name}</dd>
          <dt>
            <span id="car">
              <Translate contentKey="karadimastyresApp.customers.car">Car</Translate>
            </span>
          </dt>
          <dd>{customersEntity.car}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="karadimastyresApp.customers.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{customersEntity.notes}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="karadimastyresApp.customers.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{customersEntity.phone}</dd>
          <dt>
            <span id="tyres">
              <Translate contentKey="karadimastyresApp.customers.tyres">Tyres</Translate>
            </span>
          </dt>
          <dd>{customersEntity.tyres}</dd>
          <dt>
            <span id="plates">
              <Translate contentKey="karadimastyresApp.customers.plates">Plates</Translate>
            </span>
          </dt>
          <dd>{customersEntity.plates}</dd>
          <dt>
            <span id="customertype">
              <Translate contentKey="karadimastyresApp.customers.customertype">Customertype</Translate>
            </span>
          </dt>
          <dd>{customersEntity.customertype}</dd>
          <dt>
            <span id="lastseen">
              <Translate contentKey="karadimastyresApp.customers.lastseen">Lastseen</Translate>
            </span>
          </dt>
          <dd>{customersEntity.lastseen ? <TextFormat value={customersEntity.lastseen} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="firstseen">
              <Translate contentKey="karadimastyresApp.customers.firstseen">Firstseen</Translate>
            </span>
          </dt>
          <dd>
            {customersEntity.firstseen ? <TextFormat value={customersEntity.firstseen} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="proselesysis">
              <Translate contentKey="karadimastyresApp.customers.proselesysis">Proselesysis</Translate>
            </span>
          </dt>
          <dd>{customersEntity.proselesysis}</dd>
          <dt>
            <span id="mobile">
              <Translate contentKey="karadimastyresApp.customers.mobile">Mobile</Translate>
            </span>
          </dt>
          <dd>{customersEntity.mobile}</dd>
          <dt>
            <span id="companyphone">
              <Translate contentKey="karadimastyresApp.customers.companyphone">Companyphone</Translate>
            </span>
          </dt>
          <dd>{customersEntity.companyphone}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="karadimastyresApp.customers.email">Email</Translate>
            </span>
          </dt>
          <dd>{customersEntity.email}</dd>
          <dt>
            <span id="addressLine">
              <Translate contentKey="karadimastyresApp.customers.addressLine">Address Line</Translate>
            </span>
          </dt>
          <dd>{customersEntity.addressLine}</dd>
          <dt>
            <span id="cityCountry">
              <Translate contentKey="karadimastyresApp.customers.cityCountry">City Country</Translate>
            </span>
          </dt>
          <dd>{customersEntity.cityCountry}</dd>
        </dl>
        <Button tag={Link} to="/customers" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customers/${customersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomersDetail;
