import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBooking } from 'app/shared/model/booking.model';
import { getEntities } from './booking.reducer';

export const Booking = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const bookingList = useAppSelector(state => state.booking.entities);
  const loading = useAppSelector(state => state.booking.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="booking-heading" data-cy="BookingHeading">
        <Translate contentKey="karadimastyresApp.booking.home.title">Bookings</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="karadimastyresApp.booking.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/booking/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="karadimastyresApp.booking.home.createLabel">Create new Booking</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bookingList && bookingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="karadimastyresApp.booking.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.booking.eventdate">Eventdate</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.booking.mobile">Mobile</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.booking.brand">Brand</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.booking.model">Model</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.booking.estimatedhours">Estimatedhours</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.booking.note">Note</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bookingList.map((booking, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/booking/${booking.id}`} color="link" size="sm">
                      {booking.id}
                    </Button>
                  </td>
                  <td>{booking.eventdate ? <TextFormat type="date" value={booking.eventdate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{booking.mobile}</td>
                  <td>{booking.brand}</td>
                  <td>{booking.model}</td>
                  <td>{booking.estimatedhours}</td>
                  <td>{booking.note}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/booking/${booking.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/booking/${booking.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/booking/${booking.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="karadimastyresApp.booking.home.notFound">No Bookings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Booking;
