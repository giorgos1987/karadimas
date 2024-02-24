import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICart } from 'app/shared/model/cart.model';
import { getEntities } from './cart.reducer';

export const Cart = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const cartList = useAppSelector(state => state.cart.entities);
  const loading = useAppSelector(state => state.cart.loading);
  const totalItems = useAppSelector(state => state.cart.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="cart-heading" data-cy="CartHeading">
        <Translate contentKey="karadimastyresApp.cart.home.title">Carts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="karadimastyresApp.cart.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/cart/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="karadimastyresApp.cart.home.createLabel">Create new Cart</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cartList && cartList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="karadimastyresApp.cart.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="karadimastyresApp.cart.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('plate')}>
                  <Translate contentKey="karadimastyresApp.cart.plate">Plate</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('placeddate')}>
                  <Translate contentKey="karadimastyresApp.cart.placeddate">Placeddate</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('scheduleddate')}>
                  <Translate contentKey="karadimastyresApp.cart.scheduleddate">Scheduleddate</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('deliverydate')}>
                  <Translate contentKey="karadimastyresApp.cart.deliverydate">Deliverydate</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('brand')}>
                  <Translate contentKey="karadimastyresApp.cart.brand">Brand</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('model')}>
                  <Translate contentKey="karadimastyresApp.cart.model">Model</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numbertyres')}>
                  <Translate contentKey="karadimastyresApp.cart.numbertyres">Numbertyres</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="karadimastyresApp.cart.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('notes')}>
                  <Translate contentKey="karadimastyresApp.cart.notes">Notes</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mechanic')}>
                  <Translate contentKey="karadimastyresApp.cart.mechanic">Mechanic</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('phone')}>
                  <Translate contentKey="karadimastyresApp.cart.phone">Phone</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('address')}>
                  <Translate contentKey="karadimastyresApp.cart.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('workorder')}>
                  <Translate contentKey="karadimastyresApp.cart.workorder">Workorder</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('kilometers')}>
                  <Translate contentKey="karadimastyresApp.cart.kilometers">Kilometers</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('paymentMethod')}>
                  <Translate contentKey="karadimastyresApp.cart.paymentMethod">Payment Method</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('paymentReference')}>
                  <Translate contentKey="karadimastyresApp.cart.paymentReference">Payment Reference</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('totalPrice')}>
                  <Translate contentKey="karadimastyresApp.cart.totalPrice">Total Price</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.cart.customerDetails">Customer Details</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cartList.map((cart, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/cart/${cart.id}`} color="link" size="sm">
                      {cart.id}
                    </Button>
                  </td>
                  <td>{cart.name}</td>
                  <td>{cart.plate}</td>
                  <td>{cart.placeddate ? <TextFormat type="date" value={cart.placeddate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{cart.scheduleddate ? <TextFormat type="date" value={cart.scheduleddate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{cart.deliverydate ? <TextFormat type="date" value={cart.deliverydate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{cart.brand}</td>
                  <td>{cart.model}</td>
                  <td>{cart.numbertyres}</td>
                  <td>
                    <Translate contentKey={`karadimastyresApp.OrderStatus.${cart.status}`} />
                  </td>
                  <td>{cart.notes}</td>
                  <td>{cart.mechanic}</td>
                  <td>{cart.phone}</td>
                  <td>{cart.address}</td>
                  <td>{cart.workorder}</td>
                  <td>{cart.kilometers}</td>
                  <td>{cart.paymentMethod}</td>
                  <td>{cart.paymentReference}</td>
                  <td>{cart.totalPrice}</td>
                  <td>
                    {cart.customerDetails ? (
                      <Link to={`/customer-details/${cart.customerDetails.id}`}>{cart.customerDetails.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/cart/${cart.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/cart/${cart.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/cart/${cart.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="karadimastyresApp.cart.home.notFound">No Carts found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={cartList && cartList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Cart;
