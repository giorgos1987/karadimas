import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cart.reducer';

export const CartDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cartEntity = useAppSelector(state => state.cart.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cartDetailsHeading">
          <Translate contentKey="karadimastyresApp.cart.detail.title">Cart</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cartEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="karadimastyresApp.cart.name">Name</Translate>
            </span>
          </dt>
          <dd>{cartEntity.name}</dd>
          <dt>
            <Translate contentKey="karadimastyresApp.cart.customerDetails">Customer Details</Translate>
          </dt>
          <dd>{cartEntity.customerDetails ? cartEntity.customerDetails.name : ''}</dd>
          <dt>
            <span id="plate">
              <Translate contentKey="karadimastyresApp.cart.plate">Plate</Translate>
            </span>
          </dt>
          <dd>{cartEntity.plate}</dd>
          <dt>
            <Translate contentKey="karadimastyresApp.cart.job">Job</Translate>
          </dt>
          <dd>
            {cartEntity.jobs
              ? cartEntity.jobs.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {cartEntity.jobs && i === cartEntity.jobs.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <span id="placeddate">
              <Translate contentKey="karadimastyresApp.cart.placeddate">Placeddate</Translate>
            </span>
          </dt>
          <dd>{cartEntity.placeddate ? <TextFormat value={cartEntity.placeddate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="scheduleddate">
              <Translate contentKey="karadimastyresApp.cart.scheduleddate">Scheduleddate</Translate>
            </span>
          </dt>
          <dd>{cartEntity.scheduleddate ? <TextFormat value={cartEntity.scheduleddate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deliverydate">
              <Translate contentKey="karadimastyresApp.cart.deliverydate">Deliverydate</Translate>
            </span>
          </dt>
          <dd>{cartEntity.deliverydate ? <TextFormat value={cartEntity.deliverydate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="brand">
              <Translate contentKey="karadimastyresApp.cart.brand">Brand</Translate>
            </span>
          </dt>
          <dd>{cartEntity.brand}</dd>
          <dt>
            <span id="model">
              <Translate contentKey="karadimastyresApp.cart.model">Model</Translate>
            </span>
          </dt>
          <dd>{cartEntity.model}</dd>
          <dt>
            <span id="numbertyres">
              <Translate contentKey="karadimastyresApp.cart.numbertyres">Numbertyres</Translate>
            </span>
          </dt>
          <dd>{cartEntity.numbertyres}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="karadimastyresApp.cart.status">Status</Translate>
            </span>
          </dt>
          <dd>{cartEntity.status}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="karadimastyresApp.cart.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{cartEntity.notes}</dd>
          <dt>
            <span id="mechanic">
              <Translate contentKey="karadimastyresApp.cart.mechanic">Mechanic</Translate>
            </span>
          </dt>
          <dd>{cartEntity.mechanic}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="karadimastyresApp.cart.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{cartEntity.phone}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="karadimastyresApp.cart.address">Address</Translate>
            </span>
          </dt>
          <dd>{cartEntity.address}</dd>
          <dt>
            <span id="workorder">
              <Translate contentKey="karadimastyresApp.cart.workorder">Workorder</Translate>
            </span>
          </dt>
          <dd>{cartEntity.workorder}</dd>
          <dt>
            <span id="kilometers">
              <Translate contentKey="karadimastyresApp.cart.kilometers">Kilometers</Translate>
            </span>
          </dt>
          <dd>{cartEntity.kilometers}</dd>
          <dt>
            <span id="paymentMethod">
              <Translate contentKey="karadimastyresApp.cart.paymentMethod">Payment Method</Translate>
            </span>
          </dt>
          <dd>{cartEntity.paymentMethod}</dd>
          <dt>
            <span id="paymentReference">
              <Translate contentKey="karadimastyresApp.cart.paymentReference">Payment Reference</Translate>
            </span>
          </dt>
          <dd>{cartEntity.paymentReference}</dd>
          <dt>
            <span id="totalPrice">
              <Translate contentKey="karadimastyresApp.cart.totalPrice">Total Price</Translate>
            </span>
          </dt>
          <dd>{cartEntity.totalPrice}</dd>
        </dl>
        <Button tag={Link} to="/cart" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cart/${cartEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CartDetail;
