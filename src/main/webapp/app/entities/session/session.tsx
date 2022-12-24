import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISession } from 'app/shared/model/session.model';
import { getEntities } from './session.reducer';

export const Session = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const sessionList = useAppSelector(state => state.session.entities);
  const loading = useAppSelector(state => state.session.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="session-heading" data-cy="SessionHeading">
        <Translate contentKey="drugStoreApp.session.home.title">Sessions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="drugStoreApp.session.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/session/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="drugStoreApp.session.home.createLabel">Create new Session</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sessionList && sessionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="drugStoreApp.session.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.session.adresseIp">Adresse Ip</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.session.dateConnect">Date Connect</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.session.dateDeconnect">Date Deconnect</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.session.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.session.utilisateur">Utilisateur</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sessionList.map((session, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/session/${session.id}`} color="link" size="sm">
                      {session.id}
                    </Button>
                  </td>
                  <td>{session.adresseIp}</td>
                  <td>{session.dateConnect ? <TextFormat type="date" value={session.dateConnect} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {session.dateDeconnect ? <TextFormat type="date" value={session.dateDeconnect} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{session.user ? session.user.id : ''}</td>
                  <td>{session.utilisateur ? <Link to={`/utilisateur/${session.utilisateur.id}`}>{session.utilisateur.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/session/${session.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/session/${session.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/session/${session.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="drugStoreApp.session.home.notFound">No Sessions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Session;
