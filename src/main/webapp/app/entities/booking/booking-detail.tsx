import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './booking.reducer';

export const BookingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bookingEntity = useAppSelector(state => state.booking.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bookingDetailsHeading">
          <Translate contentKey="karadimastyresApp.booking.detail.title">Booking</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.id}</dd>
          <dt>
            <span id="eventdate">
              <Translate contentKey="karadimastyresApp.booking.eventdate">Eventdate</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.eventdate ? <TextFormat value={bookingEntity.eventdate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="mobile">
              <Translate contentKey="karadimastyresApp.booking.mobile">Mobile</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.mobile}</dd>
          <dt>
            <span id="brand">
              <Translate contentKey="karadimastyresApp.booking.brand">Brand</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.brand}</dd>
          <dt>
            <span id="model">
              <Translate contentKey="karadimastyresApp.booking.model">Model</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.model}</dd>
          <dt>
            <span id="estimatedhours">
              <Translate contentKey="karadimastyresApp.booking.estimatedhours">Estimatedhours</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.estimatedhours}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="karadimastyresApp.booking.note">Note</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.note}</dd>
        </dl>
        <Button tag={Link} to="/booking" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/booking/${bookingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BookingDetail;
