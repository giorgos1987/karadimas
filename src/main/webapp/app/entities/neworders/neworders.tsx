import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INeworders } from 'app/shared/model/neworders.model';
import { getEntities } from './neworders.reducer';

export const Neworders = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const newordersList = useAppSelector(state => state.neworders.entities);
  const loading = useAppSelector(state => state.neworders.loading);
  const totalItems = useAppSelector(state => state.neworders.totalItems);

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
      <h2 id="neworders-heading" data-cy="NewordersHeading">
        <Translate contentKey="karadimastyresApp.neworders.home.title">Neworders</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="karadimastyresApp.neworders.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/neworders/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="karadimastyresApp.neworders.home.createLabel">Create new Neworders</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {newordersList && newordersList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th hidden className="hand" onClick={sort('id')}>
                  <Translate contentKey="karadimastyresApp.neworders.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orderDate')}>
                  <Translate contentKey="karadimastyresApp.neworders.orderDate">Order Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('elastika1')}>
                  <Translate contentKey="karadimastyresApp.neworders.elastika1">Elastika 1</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('elastika2')}>
                  <Translate contentKey="karadimastyresApp.neworders.elastika2">Elastika 2</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('elastika3')}>
                  <Translate contentKey="karadimastyresApp.neworders.elastika3">Elastika 3</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('elastika4')}>
                  <Translate contentKey="karadimastyresApp.neworders.elastika4">Elastika 4</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('elastika5')}>
                  <Translate contentKey="karadimastyresApp.neworders.elastika5">Elastika 5</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('elastika6')}>
                  <Translate contentKey="karadimastyresApp.neworders.elastika6">Elastika 6</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('elastika7')}>
                  <Translate contentKey="karadimastyresApp.neworders.elastika7">Elastika 7</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('elastika8')}>
                  <Translate contentKey="karadimastyresApp.neworders.elastika8">Elastika 8</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('elastika9')}>
                  <Translate contentKey="karadimastyresApp.neworders.elastika9">Elastika 9</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('elastika10')}>
                  <Translate contentKey="karadimastyresApp.neworders.elastika10">Elastika 10</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('elastika11')}>
                  <Translate contentKey="karadimastyresApp.neworders.elastika11">Elastika 11</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('elastika12')}>
                  <Translate contentKey="karadimastyresApp.neworders.elastika12">Elastika 12</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('elastika13')}>
                  <Translate contentKey="karadimastyresApp.neworders.elastika13">Elastika 13</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {newordersList.map((neworders, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td hidden>
                    <Button tag={Link} to={`/neworders/${neworders.id}`} color="link" size="sm">
                      {neworders.id}
                    </Button>
                  </td>
                  <td>{neworders.orderDate ? <TextFormat type="date" value={neworders.orderDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{neworders.elastika1}</td>
                  <td>{neworders.elastika2}</td>
                  <td>{neworders.elastika3}</td>
                  <td>{neworders.elastika4}</td>
                  <td>{neworders.elastika5}</td>
                  <td>{neworders.elastika6}</td>
                  <td>{neworders.elastika7}</td>
                  <td>{neworders.elastika8}</td>
                  <td>{neworders.elastika9}</td>
                  <td>{neworders.elastika10}</td>
                  <td>{neworders.elastika11}</td>
                  <td>{neworders.elastika12}</td>
                  <td>{neworders.elastika13}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/neworders/${neworders.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/neworders/${neworders.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/neworders/${neworders.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="karadimastyresApp.neworders.home.notFound">No Neworders found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={newordersList && newordersList.length > 0 ? '' : 'd-none'}>
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

export default Neworders;
